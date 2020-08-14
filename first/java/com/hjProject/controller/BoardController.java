package com.hjProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.board.domain.BoardVO;
import com.board.domain.Page;
import com.board.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	@Inject
	BoardService service;

	// 게시물 목록
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void getList(Model model) throws Exception {
		List<BoardVO> list = null;
		list = service.list();

		model.addAttribute("list", list);
	}

	// 게시물 작성
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void getWrite() throws Exception {
		
	}

	// 게시물 작성
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String postWrite(BoardVO vo) throws Exception {
		System.out.println(vo.getBno());
		service.write(vo);
		return "redirect:/board/list";
	}

	// 게시물 조회
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public void getView(@RequestParam("bno") int bno, Model model) throws Exception {
		BoardVO vo = service.view(bno);
		
		// 댓글
		model.addAttribute("view", vo);
		model.addAttribute("replyList", service.getReplyList(bno));
	}

	// 게시물 수정
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void getModify(@RequestParam int bno, Model model) throws Exception {
		BoardVO vo = service.view(bno);
		model.addAttribute("view", vo);
	}

	// 게시물 수정
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String postModify(BoardVO vo) throws Exception {
		System.out.println(vo.toString());
		service.modify(vo);

		return "redirect:/board/view?bno=" + vo.getBno();
	}

	// 게시물 삭제
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String getDelete(@RequestParam("bno") int bno) throws Exception {

		service.delete(bno);
		return "redirect:/board/list";
	}

	// 게시물 목록 + 페이징 추가
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public void getListPage(Model model, @RequestParam("num") int num) throws Exception {

		Page page = new Page();
		page.setNum(num);
		page.setCount(service.count());

		List<BoardVO> list = null;
		list = service.listPage(page.getDisplayPost(), page.getPostNum());
		model.addAttribute("list", list);

		model.addAttribute("page", page);

		model.addAttribute("select", num);
	}

	// 게시물 목록 + 페이징 추가 + 검색
	@RequestMapping(value = "/listPageSearch", method = RequestMethod.GET)
	public void getListPageSearch(Model model, @RequestParam("num") int num,
			@RequestParam(value = "searchType", required = false, defaultValue = "title") String searchType,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) throws Exception {

		Page page = new Page();
		page.setNum(num);
		// page.setCount(service.count());
		page.setCount(service.searchCount(searchType, keyword));
		page.setSearchType(searchType);
		page.setKeyword(keyword);

		List<BoardVO> list = null;
		list = service.listPageSearch(page.getDisplayPost(), page.getPostNum(), searchType, keyword);

		model.addAttribute("list", list);
		model.addAttribute("page", page);
		model.addAttribute("select", num);

		model.addAttribute("searchType", searchType);
		model.addAttribute("keyword", keyword);
	}

	// 댓글 등록  (ajax)
	@RequestMapping(value = "/reply/save", method = RequestMethod.POST)
	@ResponseBody
	public Object boardReplySave(@RequestParam Map<String, Object> paramMap) {
		// 호출한 곳에 response
		Map<String, Object> retVal = new HashMap<String, Object>();
		//System.out.println("hi");

		// 패스워드 암호화
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
		paramMap.put("reply_password", password);

		int result = service.regReply(paramMap);

		//System.out.println("mapping end");
		if (result > 0) {
			retVal.put("code", "OK");
			retVal.put("reply_id", paramMap.get("reply_id"));
			System.out.println(retVal.get("reply_id"));
			retVal.put("parant_id", paramMap.get("parent_id"));
			retVal.put("message", "등록에 성공 하였습니다.");
		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "등록에 실패 했습니다.");
		}

		return retVal;
	}

	// 댓글 삭제 (ajax)
	@RequestMapping(value = "/reply/del", method = RequestMethod.POST)
	@ResponseBody
	public Object boardReplyDel(@RequestParam Map<String, Object> paramMap) {
		//System.out.println(paramMap.get("reply_password").toString());
		//System.out.println(paramMap.get("reply_id".toString()));
		
		
		// 리턴값
		Map<String, Object> retVal = new HashMap<String, Object>();
		
		
		// 패스워드 암호화
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
		
		paramMap.put("reply_password", password);

		// 정보입력
		int result = service.delReply(paramMap);
		
		if (result > 0) {
			retVal.put("code", "OK");
		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "삭제에 실패했습니다. 패스워드를 확인해주세요.");
		}

		return retVal;
	}
	
	//댓글 수정	(ajax)
	@RequestMapping(value = "/reply/update", method = RequestMethod.POST)
	@ResponseBody
	public Object boardReplyUpdate(@RequestParam Map<String, Object> paramMap) {
		//리턴값
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //패스워드 암호화
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
        paramMap.put("reply_password", password);
 
        System.out.println(paramMap);
 
        //정보입력
        boolean check = service.updateReply(paramMap);
 
        if(check){
            retVal.put("code", "OK");
            retVal.put("reply_id", paramMap.get("reply_id"));
            retVal.put("message", "수정에 성공 하였습니다.");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "수정에 실패 하였습니다.");
        }
 
        return retVal;
	}
	
	//댓글 패스워드 확인 (ajax)
	 @RequestMapping(value="/board/reply/check", method=RequestMethod.POST)
	    @ResponseBody
	    public Object boardReplyCheck(@RequestParam Map<String, Object> paramMap) {
	 
	        //리턴값
	        Map<String, Object> retVal = new HashMap<String, Object>();
	 
	        //패스워드 암호화
	        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
	        String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
	        paramMap.put("reply_password", password);
	 
	        //정보입력
	        boolean check = service.checkReply(paramMap);
	 
	        if(check){
	            retVal.put("code", "OK");
	            retVal.put("reply_id", paramMap.get("reply_id"));
	        }else{
	            retVal.put("code", "FAIL");
	            retVal.put("message", "패스워드를 확인해 주세요.");
	        }
	 
	        return retVal;
	 
	    }
}

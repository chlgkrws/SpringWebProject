package com.hjProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.board.domain.BoardVO;
import com.board.domain.Page;
import com.board.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	private Logger log = LoggerFactory.getLogger(BoardController.class);
	
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
	@ResponseBody
	public Map<String, Object> postWrite(@RequestParam Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		
		int result = service.write(paramMap);
		
		Map<String, Object> retVal = new HashMap<String, Object>();
		
		if(result > 0) {
			retVal.put("code", "OK");
			retVal.put("message", "게시물을 업로드했습니다.");
			log.info(request.getRemoteAddr()+ "에서 "+paramMap.get("writer") +"님이"+paramMap.get("title")+" 글을 작성했습니다.");
		}else {
			retVal.put("code", "FAIL");
			retVal.put("message", "게시물을 업로드하지 못했습니다.");
			log.info(request.getRemoteAddr()+ "에서 "+paramMap.get("writer") +"님이"+paramMap.get("title")+ " 작성을 실패했습니다.");
		}
		return retVal;
	}

	// 게시물 조회
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public void getView(@RequestParam("bno") int bno, Model model, HttpServletRequest request) throws Exception {
		BoardVO vo = service.view(bno);
		service.viewCnt(bno);	//조회수 증가
		// 댓글
		
		if(request.getUserPrincipal().getName().equals(vo.getStudent_id())) {
			model.addAttribute("writer", "YES");
		}
		model.addAttribute("view", vo);
		model.addAttribute("replyList", service.getReplyList(bno));
		log.info(bno+"글에 "+request.getRemoteUser()+"님이 "+request.getRemoteAddr()+"에서 조회했습니다.");
		
	}

	// 게시물 수정
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView getModify(@RequestParam int bno, ModelAndView model, HttpServletRequest request) throws Exception {
		BoardVO vo = service.view(bno);
		/* model.addAttribute("view", vo) */;
		
		ModelAndView mv = new ModelAndView();
		// /modify url로 접근했을 때 본인이 아니면 막는 로직
		if(!vo.getStudent_id().equals(request.getUserPrincipal().getName())) {
			System.out.println("bug");
			mv.setViewName("/board/view?bno="+bno);
			return mv;
		}
		
		mv.addObject("view", vo);			//vo
		mv.addObject("modify", "YES");		//수정인지 아닌지 체크
		mv.addObject("bno", bno);			//글 번호
		mv.setViewName("/board/write");
		
		return mv;
	}

	// 게시물 수정
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> postModify(@RequestParam Map<String, Object>paramMap, @RequestParam int bno, HttpServletRequest request) throws Exception {
		Map<String, Object> retVal = new HashMap<String, Object>();
		
		paramMap.put("bno", bno);
		int result = service.modify(paramMap);
		if(result > 0) {
			retVal.put("code", "OK");
			retVal.put("message", "게시물을 수정했습니다.");
			log.info(request.getRemoteAddr()+ "에서 "+paramMap.get("writer") +"님이("+bno+")"+paramMap.get("title")+" 글을 수정했습니다.");
		}else {
			retVal.put("code", "FAIL");
			retVal.put("message", "게시물을 수정하지 못했습니다.");
			log.info(request.getRemoteAddr()+ "에서 "+paramMap.get("writer") +"님이("+bno+")"+paramMap.get("title")+ " 수정을 실패했습니다.");
		}
		return retVal;
		
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
	public Object boardReplySave(@RequestParam Map<String, Object> paramMap,HttpServletRequest request) {
		// 호출한 곳에 response
		Map<String, Object> retVal = new HashMap<String, Object>();
		//System.out.println("hi");

		// 패스워드 암호화
		/*
		 * ShaPasswordEncoder encoder = new ShaPasswordEncoder(256); String password =
		 * encoder.encodePassword(paramMap.get("reply_password").toString(), null);
		 * paramMap.put("reply_password", password);
		 */

		int result = service.regReply(paramMap);

		//System.out.println("mapping end");
		if (result > 0) {
			retVal.put("code", "OK");
			retVal.put("reply_id", paramMap.get("reply_id"));
			retVal.put("parant_id", paramMap.get("parent_id"));
			retVal.put("message", "등록에 성공 하였습니다.");
			log.info(paramMap.get("board_bno")+"번 글에서 "+paramMap.get("student_id")+"님이 reply_id: " +paramMap.get("reply_id")+"번 댓글을 등록했습니다. ip = "+request.getRemoteAddr());
		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "등록에 실패 했습니다.");
			log.info(paramMap.get("board_bno")+"번 글에서 "+paramMap.get("student_id")+"님이 reply_id: " +paramMap.get("reply_id")+"번 댓글 등록에 실패했습니다.. ip = "+request.getRemoteAddr());

		}

		return retVal;
	}

	// 댓글 삭제 (ajax)
	@RequestMapping(value = "/reply/del", method = RequestMethod.POST)
	@ResponseBody
	public Object boardReplyDel(@RequestParam Map<String, Object> paramMap, HttpServletRequest request) {
		//System.out.println(paramMap.get("reply_password").toString());
		//System.out.println(paramMap.get("reply_id".toString()));
		
		// 리턴값
		Map<String, Object> retVal = new HashMap<String, Object>();
		
		/*
		 * // 패스워드 암호화 ShaPasswordEncoder encoder = new ShaPasswordEncoder(256); String
		 * password = encoder.encodePassword(paramMap.get("reply_password").toString(),
		 * null);
		 * 	paramMap.put("reply_password", password);
		 */

		// 정보입력
		int result = service.delReply(paramMap);
		
		if (result > 0) {
			retVal.put("code", "OK");
			log.info(paramMap.get("board_bno")+"번 글에서 "+paramMap.get("student_id")+"님이 reply_id: " +paramMap.get("reply_id")+"번 댓글을 삭제했습니다. ip = "+request.getRemoteAddr());

		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "삭제에 실패했습니다. 자신의 댓글인지 확인해 주세요.");
			log.info(paramMap.get("board_bno")+"번 글에서 "+paramMap.get("student_id")+"님이 reply_id: " +paramMap.get("reply_id")+"번 댓글 삭제에 실패했습니다. ip = "+request.getRemoteAddr());

		}

		return retVal;
	}
	
	//댓글 수정(ajax)
	@RequestMapping(value = "/reply/update", method = RequestMethod.POST)
	@ResponseBody
	public Object boardReplyUpdate(@RequestParam Map<String, Object> paramMap, HttpServletRequest request) {
		//리턴값
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //패스워드 암호화
		/*
		 * ShaPasswordEncoder encoder = new ShaPasswordEncoder(256); String password =
		 * encoder.encodePassword(paramMap.get("reply_password").toString(), null);
		 * paramMap.put("reply_password", password);
		 */
       // System.out.println(paramMap);
 
        //정보입력
        boolean check = service.updateReply(paramMap);
 
        if(check){
            retVal.put("code", "OK");
            retVal.put("reply_id", paramMap.get("reply_id"));
            retVal.put("message", "수정에 성공 하였습니다.");
            log.info(paramMap.get("board_bno")+"번 글에서 "+paramMap.get("student_id")+"님이 reply_id: " +paramMap.get("reply_id")+"번의 댓글을 수정했습니다. ip = "+request.getRemoteAddr());
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "수정에 실패 하였습니다.");
            log.info(paramMap.get("board_bno")+"번 글에서 "+paramMap.get("student_id")+"님이 reply_id: " +paramMap.get("reply_id")+"번의 댓글 수정을 실패했습니다. ip = "+request.getRemoteAddr());

        }
 
        return retVal;
	}
	
	//댓글 사용자 확인 (ajax)
	 @RequestMapping(value="/reply/check", method=RequestMethod.POST)
	    @ResponseBody
	    public Object boardReplyCheck(@RequestParam Map<String, Object> paramMap) {
	 
	        //리턴값
	        Map<String, Object> retVal = new HashMap<String, Object>();
	 
			/*
			 * //패스워드 암호화 ShaPasswordEncoder encoder = new ShaPasswordEncoder(256); String
			 * password = encoder.encodePassword(paramMap.get("reply_password").toString(),
			 * null); paramMap.put("reply_password", password);
			 */
	 
	        //정보입력
	        boolean check = service.checkReply(paramMap);
	 
	        if(check){
	            retVal.put("code", "OK");
	            retVal.put("reply_id", paramMap.get("reply_id"));
	        }else{
	            retVal.put("code", "FAIL");
	            retVal.put("message", "자신의 댓글만 수정할 수 있습니다.");
	        }
	 
	        return retVal;
	 
	    }
}

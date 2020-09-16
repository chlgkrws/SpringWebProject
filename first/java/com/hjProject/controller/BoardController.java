package com.hjProject.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.spi.CalendarDataProvider;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.board.domain.BoardReplyVO;
import com.board.domain.BoardVO;
import com.board.domain.Page;
import com.board.service.BoardService;

@Controller
@Scope("session")
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
	public void getWrite(@RequestParam(value = "boardType", required = false, defaultValue = "") String boardType,
			@RequestParam(value = "listType", required = false, defaultValue = "") String listType, Model model)
			throws Exception {

		model.addAttribute("boardType", boardType);
		model.addAttribute("listType", listType);
	}

	// 게시물 작성
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> postWrite(@RequestParam Map<String, Object> paramMap,
			@RequestParam(value = "boardType", required = false, defaultValue = "") String boardType,
			@RequestParam(value = "listType", required = false, defaultValue = "") String listType,
			HttpServletRequest request) throws Exception {

		paramMap.put("boardType", boardType);
		paramMap.put("listType", listType);

		int result = service.write(paramMap);

		Map<String, Object> retVal = new HashMap<String, Object>();

		if (result > 0) {
			retVal.put("code", "OK");
			retVal.put("message", "게시물을 업로드했습니다.");
			log.info(request.getRemoteAddr() + "에서 " + paramMap.get("writer") + "님이" + paramMap.get("title")
					+ " 글을 작성했습니다.");
		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "게시물을 업로드하지 못했습니다.");
			log.info(request.getRemoteAddr() + "에서 " + paramMap.get("writer") + "님이" + paramMap.get("title")
					+ " 작성을 실패했습니다.");
		}
		return retVal;
	}

	// 게시물 조회
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public void getView(@RequestParam("bno") int bno, Model model,
			@RequestParam(value = "boardType", required = false, defaultValue = "") String boardType,
			@RequestParam(value = "listType", required = false, defaultValue = "") String listType,
			HttpServletRequest request) throws Exception {
		
		
		BoardVO vo = service.view(bno, boardType, listType);
		List<BoardReplyVO> voR = service.getReplyList(bno, boardType, listType);
		service.viewCnt(bno, boardType, listType); // 조회수 증가
		LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
		lhm = getSideMenu(boardType, listType);											//해당 Board에 해당하는 listType가져오기
		
		if (request.getUserPrincipal().getName().equals(vo.getStudent_id())) {
			model.addAttribute("writer", "YES");
		}
		model.addAttribute("view", vo);
		model.addAttribute("date", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(vo.getRegDate()));				//게시물 작성시간
		model.addAttribute("replyList", service.getReplyList(bno, boardType, listType));
		model.addAttribute("boardType", boardType);
		model.addAttribute("listType", listType);
		model.addAttribute("listMenu",lhm);

		log.info(boardType + "메뉴 " + listType + "인 리스트에서 " + bno + "번 째 글에 " + request.getRemoteUser() + "님이 "
				+ request.getRemoteAddr() + "에서 조회했습니다.");

	}

	// 게시물 수정
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView getModify(@RequestParam int bno,
			@RequestParam(value = "boardType", required = false, defaultValue = "") String boardType,
			@RequestParam(value = "listType", required = false, defaultValue = "") String listType, ModelAndView model,
			HttpServletRequest request) throws Exception {

		BoardVO vo = service.view(bno, boardType, listType);
		/* model.addAttribute("view", vo) */;

		ModelAndView mv = new ModelAndView();
		// /modify url로 접근했을 때 본인이 아니면 막는 로직
		if (!vo.getStudent_id().equals(request.getUserPrincipal().getName())) {
			System.out.println("bug");
			mv.setViewName("/");
			return mv;
		}

		mv.addObject("view", vo); // vo
		mv.addObject("modify", "YES"); // 수정인지 아닌지 체크
		mv.addObject("bno", bno); // 글 번호
		mv.addObject("boardType", boardType);
		mv.addObject("listType", listType);
		mv.setViewName("/board/write");
		// ?boardType="+boardType+"listType"+listType
		return mv;
	}

	// 게시물 수정
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> postModify(@RequestParam Map<String, Object> paramMap, @RequestParam int bno,
			@RequestParam(value = "boardType", required = false, defaultValue = "") String boardType,
			@RequestParam(value = "listType", required = false, defaultValue = "") String listType,
			HttpServletRequest request) throws Exception {

		Map<String, Object> retVal = new HashMap<String, Object>();

		paramMap.put("bno", bno);
		paramMap.put("boardType", boardType);
		paramMap.put("listType", listType);
		System.out.println(boardType + " 123123" + listType);
		int result = service.modify(paramMap);
		if (result > 0) {
			retVal.put("code", "OK");
			retVal.put("message", "게시물을 수정했습니다.");
			log.info(request.getRemoteAddr() + "에서 " + paramMap.get("writer") + "님이(" + bno + ")"
					+ paramMap.get("title") + " 글을 수정했습니다.");
		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "게시물을 수정하지 못했습니다.");
			log.info(request.getRemoteAddr() + "에서 " + paramMap.get("writer") + "님이(" + bno + ")"
					+ paramMap.get("title") + " 수정을 실패했습니다.");
		}
		return retVal;

	}

	// 게시물 삭제
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String getDelete(@RequestParam("bno") int bno,
			@RequestParam(value = "boardType", required = false, defaultValue = "") String boardType,
			@RequestParam(value = "listType", required = false, defaultValue = "") String listType,
			HttpServletRequest request) throws Exception {

		service.delete(bno, boardType, listType);
		log.info(request.getRemoteUser() + "님이 " + request.getRemoteAddr() + "에서 " + boardType + "타입의 " + listType
				+ "리스트 중 " + bno + "번 째 게시물을 삭제했습니다.");
		return "redirect:/board/listPageSearch?num=1&boardType=board&listType=free";
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
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "boardType", required = false, defaultValue = "") String boardType,
			@RequestParam(value = "listType", required = false, defaultValue = "") String listType,
			HttpServletRequest request) throws Exception {
		String boardT = boardType;
		String listT = listType;
		
		//사이드 메뉴 값을 받아오기 위한 hashMap
		LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();
		hm = getSideMenu(boardType, listType);
		

		Page page = new Page();
		page.setNum(num);
		// page.setCount(service.count());
		page.setCount(service.searchCount(searchType, keyword, boardT, listT));
		page.setSearchType(searchType);
		page.setKeyword(keyword);
		page.setBoardType(boardT);
		page.setListType(listT);

		List<BoardVO> list = null;
		list = service.listPageSearch(page.getDisplayPost(), page.getPostNum(), searchType, keyword, boardT, listT);

		model.addAttribute("listMenu", hm);
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		model.addAttribute("select", num);

		model.addAttribute("searchType", searchType);
		model.addAttribute("keyword", keyword);
		model.addAttribute("boardType", boardType);
		model.addAttribute("listType", listType);

		if (request.getRemoteUser() == null) {
			log.info(request.getRemoteAddr() + "에서 " + boardT + "타입의 " + listT + "리스트를 조회했습니다.");
		} else {
			log.info(request.getRemoteUser() + "님이 " + request.getRemoteAddr() + "에서 " + boardT + "타입의 " + listT
					+ "리스트를 조회했습니다.");
		}
	}

	// 댓글 등록 (ajax)
	@RequestMapping(value = "/reply/save", method = RequestMethod.POST)
	@ResponseBody
	public Object boardReplySave(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, Locale locale) {
		// 호출한 곳에 response
		Map<String, Object> retVal = new HashMap<String, Object>();
		// System.out.println("hi");

		// 패스워드 암호화
		/*
		 * ShaPasswordEncoder encoder = new ShaPasswordEncoder(256); String password =
		 * encoder.encodePassword(paramMap.get("reply_password").toString(), null);
		 * paramMap.put("reply_password", password);
		 */

		int result = service.regReply(paramMap);

		// System.out.println("mapping end");
		if (result > 0) {
			retVal.put("code", "OK");
			retVal.put("reply_id", paramMap.get("reply_id"));
			retVal.put("parant_id", paramMap.get("parent_id"));
			retVal.put("message", "등록에 성공 하였습니다.");
			retVal.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
			System.out.println(retVal.get("date"));
			log.info(paramMap.get("board_bno") + "번 글에서 " + paramMap.get("student_id") + "님이 reply_id: "
					+ paramMap.get("reply_id") + "번 댓글을 등록했습니다. ip = " + request.getRemoteAddr());
		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "등록에 실패 했습니다.");
			log.info(paramMap.get("board_bno") + "번 글에서 " + paramMap.get("student_id") + "님이 reply_id: "
					+ paramMap.get("reply_id") + "번 댓글 등록에 실패했습니다.. ip = " + request.getRemoteAddr());

		}

		return retVal;
	}

	// 댓글 삭제 (ajax)
	@RequestMapping(value = "/reply/del", method = RequestMethod.POST)
	@ResponseBody
	public Object boardReplyDel(@RequestParam Map<String, Object> paramMap, HttpServletRequest request) {
		// System.out.println(paramMap.get("reply_password").toString());
		// System.out.println(paramMap.get("reply_id".toString()));

		// 리턴값
		Map<String, Object> retVal = new HashMap<String, Object>();

		/*
		 * // 패스워드 암호화 ShaPasswordEncoder encoder = new ShaPasswordEncoder(256); String
		 * password = encoder.encodePassword(paramMap.get("reply_password").toString(),
		 * null); paramMap.put("reply_password", password);
		 */

		// 정보입력
		int result = service.delReply(paramMap);

		if (result > 0) {
			retVal.put("code", "OK");
			log.info(paramMap.get("board_bno") + "번 글에서 " + paramMap.get("student_id") + "님이 reply_id: "
					+ paramMap.get("reply_id") + "번 댓글을 삭제했습니다. ip = " + request.getRemoteAddr());

		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "삭제에 실패했습니다. 자신의 댓글인지 확인해 주세요.");
			log.info(paramMap.get("board_bno") + "번 글에서 " + paramMap.get("student_id") + "님이 reply_id: "
					+ paramMap.get("reply_id") + "번 댓글 삭제에 실패했습니다. ip = " + request.getRemoteAddr());

		}

		return retVal;
	}

	// 댓글 수정(ajax)
	@RequestMapping(value = "/reply/update", method = RequestMethod.POST)
	@ResponseBody
	public Object boardReplyUpdate(@RequestParam Map<String, Object> paramMap, HttpServletRequest request) {
		// 리턴값
		Map<String, Object> retVal = new HashMap<String, Object>();

		// 패스워드 암호화
		/*
		 * ShaPasswordEncoder encoder = new ShaPasswordEncoder(256); String password =
		 * encoder.encodePassword(paramMap.get("reply_password").toString(), null);
		 * paramMap.put("reply_password", password);
		 */
		// System.out.println(paramMap);

		// 정보입력
		boolean check = service.updateReply(paramMap);

		if (check) {
			retVal.put("code", "OK");
			retVal.put("reply_id", paramMap.get("reply_id"));
			retVal.put("message", "수정에 성공 하였습니다.");
			log.info(paramMap.get("board_bno") + "번 글에서 " + paramMap.get("student_id") + "님이 reply_id: "
					+ paramMap.get("reply_id") + "번의 댓글을 수정했습니다. ip = " + request.getRemoteAddr());
		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "수정에 실패 하였습니다.");
			log.info(paramMap.get("board_bno") + "번 글에서 " + paramMap.get("student_id") + "님이 reply_id: "
					+ paramMap.get("reply_id") + "번의 댓글 수정을 실패했습니다. ip = " + request.getRemoteAddr());

		}

		return retVal;
	}

	// 댓글 사용자 확인 (ajax)
	@RequestMapping(value = "/reply/check", method = RequestMethod.POST)
	@ResponseBody
	public Object boardReplyCheck(@RequestParam Map<String, Object> paramMap) {

		// 리턴값
		Map<String, Object> retVal = new HashMap<String, Object>();

		/*
		 * //패스워드 암호화 ShaPasswordEncoder encoder = new ShaPasswordEncoder(256); String
		 * password = encoder.encodePassword(paramMap.get("reply_password").toString(),
		 * null); paramMap.put("reply_password", password);
		 */

		// 정보입력
		boolean check = service.checkReply(paramMap);

		if (check) {
			retVal.put("code", "OK");
			retVal.put("reply_id", paramMap.get("reply_id"));
		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "자신의 댓글만 수정할 수 있습니다.");
		}

		return retVal;
	
	}
	
	
	File fileDir = new File("C:\\spring_Image\\");

	@RequestMapping(value = "/fileUpload", method = RequestMethod.GET)
	public void getImageUpload(HttpServletRequest request, HttpServletResponse response,
			 @RequestParam MultipartFile upload) throws Exception {
		System.out.println("하잉");
	}
	
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public void imageUpload(HttpServletRequest request, HttpServletResponse response,
			 @RequestParam MultipartFile upload) throws Exception {
		// 랜덤 문자 생성
		UUID uid = UUID.randomUUID();
		System.out.println("하잉2");
		OutputStream out = null;
		PrintWriter printWriter = null;

		// 인코딩
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		try {

			// 파일 이름 가져오기
			String fileName = upload.getOriginalFilename();
			byte[] bytes = upload.getBytes();

			// 이미지 경로 생성
			String path = fileDir.getPath() + "ckImage/";// fileDir는 전역 변수라 그냥 이미지 경로 설정해주면 된다.
			String ckUploadPath = path + uid + "_" + fileName;
			File folder = new File(path);

			// 해당 디렉토리 확인
			if (!folder.exists()) {
				try {
					folder.mkdirs(); // 폴더 생성
				} catch (Exception e) {
					e.getStackTrace();
				}
			}
			
			out = new FileOutputStream(new File(ckUploadPath));
			out.write(bytes);
			out.flush(); // outputStram에 저장된 데이터를 전송하고 초기화

			String callback = request.getParameter("CKEditorFuncNum");
			printWriter = response.getWriter();
			String fileUrl = "/board/ckImgSubmit.do?uid=" + uid + "&fileName=" + fileName; // 작성화면

			// 업로드시 메시지 출력
			printWriter.println("{\"filename\" : \"" + fileName + "\", \"uploaded\" : 1, \"url\":\"" + fileUrl + "\"}");
			printWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (printWriter != null) {
					printWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return;
	}

	/**
	     * cKeditor 서버로 전송된 이미지 뿌려주기
	     * @param uid
	     * @param fileName
	     * @param request
	     * @return
	     * @throws ServletException
	     * @throws IOException
	     */
	    //
	    @RequestMapping(value="/ckImgSubmit.do")
	    public void ckSubmit(@RequestParam(value="uid") String uid
	                            , @RequestParam(value="fileName") String fileName
	                            , HttpServletRequest request, HttpServletResponse response)
	 throws ServletException, IOException{
	        
	        //서버에 저장된 이미지 경로
	        String path = fileDir.getPath() + "ckImage/";
	    
	        String sDirPath = path + uid + "_" + fileName;
	    
	        File imgFile = new File(sDirPath);
	        
	        //사진 이미지 찾지 못하는 경우 예외처리로 빈 이미지 파일을 설정한다.
	        if(imgFile.isFile()){
	            byte[] buf = new byte[1024];
	            int readByte = 0;
	            int length = 0;
	            byte[] imgBuf = null;
	            
	            FileInputStream fileInputStream = null;
	            ByteArrayOutputStream outputStream = null;
	            ServletOutputStream out = null;
	            
	            try{
	                fileInputStream = new FileInputStream(imgFile);
	                outputStream = new ByteArrayOutputStream();
	                out = response.getOutputStream();
	                
	                while((readByte = fileInputStream.read(buf)) != -1){
	                    outputStream.write(buf, 0, readByte);
	                }
	                
	                imgBuf = outputStream.toByteArray();
	                length = imgBuf.length;
	                out.write(imgBuf, 0, length);
	                out.flush();
	                
	            }catch(IOException e){
	                log.info("에러");
	            }finally {
	                outputStream.close();
	                fileInputStream.close();
	                out.close();
	            }
	        }
	    }
	    
	    //해당 board가 가지고 있는 list값을 반환하는 메서드
	    public LinkedHashMap<String, String> getSideMenu(String boardType, String listType){
	    	ArrayList<String[]> listMenu = new ArrayList<>();
			ArrayList<String> resultMenu = new ArrayList<>();
			LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();

			String[] menuEn = { "notice", "tip", "free", "study", "qna", "share" };
			String[] menuKo = { "공지사항", "졸업&수업 꿀팁", "자유게시판", "수업게시판", "Q&A", "전공 책 나눔" };

			listMenu.add(new String[] { "notice", "tip" });
			listMenu.add(new String[] { "free", "study", "qna" });
			listMenu.add(new String[] { "share" });

			// 해당 리스트에 보드에 어떤 리스트들이 있는지 찾는 로직
			for (int i = 0; i < listMenu.size(); i++) {
				for (int j = 0; j < listMenu.get(i).length; j++) {
					if (listMenu.get(i)[j].equals(listType)) {
						j = 0;
						for (int q = 0; q < listMenu.get(i).length; q++) {
							resultMenu.add(listMenu.get(i)[j++]);
						}
					}
				}
			}

			for (int i = 0; i < resultMenu.size(); i++) {
				for (int j = 0; j < menuEn.length; j++) {
					if (resultMenu.get(i).equals(menuEn[j])) {
						hm.put(menuEn[j], menuKo[j]);
					}
				}
			}
	    	return hm;
	    }
}

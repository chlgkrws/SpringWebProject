package com.hjProject.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.board.domain.BoardVO;
import com.board.service.BoardService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	BoardService service;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		logger.info("Welcome home! The client locale is {}.", locale+" 시간 : "+ formattedDate+" 접속 ip : "+request.getRemoteAddr());
		
		
		//가장 많은 조회 게시물
		List<BoardVO> highViewCnt = service.highViewCnt();
		//가장 최근 게시물
		List<BoardVO> recentView = service.recentView();
		
		model.addAttribute("highViewCnt",highViewCnt);
		model.addAttribute("recentView",recentView);
		return "home";
	}
	
}

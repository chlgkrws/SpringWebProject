package com.hjProject.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sign.service.SignService;


@Controller
@RequestMapping("/sign/*")
public class SignController {
	private Logger log = LoggerFactory.getLogger(SignController.class);
	
	@Inject
	SignService service;
	
	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public void getSignUp() {
	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	@ResponseBody
	public Object postSignUp(@RequestParam Map<String, Object> paramMap, HttpServletRequest request) {
		
		
		Map<String, Object> retVal = new HashMap<String, Object>();
		/*
		 * System.out.println(paramMap.get("student_id").toString());
		 * System.out.println(paramMap.get("student_name").toString());
		 * System.out.println(paramMap.get("student_password").toString());
		 */
		System.out.println(paramMap.get("student_name")+"님이 회원가입 시도 중...");
		//ShaPasswordEncoder encoder = new ShaPasswordEncoder();
		//String password = encoder.encodePassword(paramMap.get("student_password").toString(), null);
		BCryptPasswordEncoder bEncoder = new BCryptPasswordEncoder();
		String password = bEncoder.encode(paramMap.get("student_password").toString());
		paramMap.put("student_password", password);
		//System.out.println(paramMap.get("student_password").toString());
		
		String student_id = paramMap.get("student_id").toString();
		System.out.println(student_id);
		boolean signedCheck = service.signedCheck(student_id);
		if(!signedCheck) {
			retVal.put("code", "FAIL");
			retVal.put("message","학번과 이름이 일치하지 않거나 이미 회원가입한 학생입니다.");
			log.info(paramMap.get("student_id") + "("+paramMap.get("student_name")+")님이 "+request.getRemoteAddr()+"에서 회원가입에 실패했습니다.(1)");
			return retVal;
		}
		
		
		boolean check = service.sign_up(paramMap);
		if(check) {
			retVal.put("code", "OK");
			retVal.put("message", "회원가입이 성공적으로 끝났습니다.");
			log.info(paramMap.get("student_id") + "("+paramMap.get("student_name")+")님이 "+request.getRemoteAddr()+"에서 회원가입했습니다.");
		}else {
			retVal.put("code", "FAIL");
			retVal.put("message", "학번과 이름이 일치하지 않거나 존재하지 않는 회원입니다.");
			log.info(paramMap.get("student_id") + "("+paramMap.get("student_name")+")님이 "+request.getRemoteAddr()+"에서 회원가입에 실패했습니다.(2)");
		}
		
		return retVal;
	}
	
	@RequestMapping(value = "/customLogin",method = RequestMethod.GET)
	public void getLogin(HttpSession session,String error, String logout,HttpServletRequest request) {
		System.out.println("hi this is /login get");
		log.info("this is customLogin");
		
	}

	@RequestMapping(value = "/customLogin",method = RequestMethod.POST)
	public void Login(HttpSession session) {
		System.out.println("hi this is /login post");
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) 
	{
		session.invalidate();
		return "redirect:/";
	}


}

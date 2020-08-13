package com.hjProject.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sign.domain.StudentVO;
import com.sign.service.SignService;

@Controller
@RequestMapping(value="/sign")
public class SignController {
	
	@Inject
	SignService service;
	
	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public void getSignUp() {
	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	@ResponseBody
	public Object signUp(@RequestParam Map<String, Object> paramMap) {
		Map<String, Object> retVal = new HashMap<String, Object>();
		/*
		 * System.out.println(paramMap.get("student_id").toString());
		 * System.out.println(paramMap.get("student_name").toString());
		 * System.out.println(paramMap.get("student_password").toString());
		 */
		
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(paramMap.get("student_password").toString(), null);
		
		paramMap.put("student_password", password);
		//System.out.println(paramMap.get("student_password").toString());
		
		String student_id = paramMap.get("student_id").toString();
		
		boolean signedCheck = service.signedCheck(student_id);
		if(!signedCheck) {
			retVal.put("code", "FAIL");
			retVal.put("message","학번과 이름이 일치하지 않거나 이미 회원가입한 학생입니다.");
			return retVal;
		}
		
		
		boolean check = service.sign_up(paramMap);
		if(check) {
			retVal.put("code", "OK");
			retVal.put("message", "회원가입이 성공적으로 끝났습니다.");
		}else {
			retVal.put("code", "FAIL");
			retVal.put("message", "학번과 이름이 일치하지 않거나 존재하지 않는 회원입니다.");
		}
		
		return retVal;
	}


}

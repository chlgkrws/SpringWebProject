package com.hjProject.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jdk.internal.jline.internal.Log;

@Controller
public class CommonController {
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		Log.info("access Denied : " + auth);
		model.addAttribute("msg","Access Denied");
	}
	
}

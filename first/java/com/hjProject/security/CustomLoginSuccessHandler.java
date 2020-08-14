package com.hjProject.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.executor.ReuseExecutor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jdk.internal.jline.internal.Log;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		List<String> roleNames = new ArrayList<String>();
		
		authentication.getAuthorities().forEach(authority ->{
			roleNames.add(authority.getAuthority());
			System.out.println(authority.getAuthority()+" hi");
		});
		
		/* Log.warn("ROLE NAMES : "+roleNames); */
		
		if(roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("/board/listPageSearch?num=1");
			return;
		}
		
		if(roleNames.contains("ROLE_MEMBER")) {
		
			response.sendRedirect("/board/listPageSearch?num=1");
			
			request.getSession().setAttribute("id", "member");
			request.getSession().setAttribute("password", "member");

			return;
		
		}else {
			response.sendRedirect("/");
		}
		
	}

}

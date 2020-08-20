package com.hjProject.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.sign.service.SignServiceImpl;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{
	String student_id;
	String student_name;
	String defaultUrl;
	
	@Inject
	SignServiceImpl service;
	
	private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		student_name = service.selectName(student_id);
		
		List<String> roleNames = new ArrayList<String>();
		authentication.getAuthorities().forEach(authority ->{
			roleNames.add(authority.getAuthority());
			System.out.println(authority.getAuthority()+" hi");
		});
		
		if(roleNames.contains("ROLE_ADMIN")) {
			request.getSession().setAttribute("student_name", student_name);
			request.getSession().setAttribute("student_id", student_id);
			resultRedirectStrategy(request, response, authentication);

			return;
		}
		
		if(roleNames.contains("ROLE_MEMBER")) {
			request.getSession().setAttribute("student_name", student_name);
			request.getSession().setAttribute("student_id", student_id);
			resultRedirectStrategy(request, response, authentication);

			return;
		
		}else {
			response.sendRedirect("/");
		}
		
	}
	
	 protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response,
	            Authentication authentication) throws IOException, ServletException {
	        
	        SavedRequest savedRequest = requestCache.getRequest(request, response);
	        
	        if(savedRequest!=null) {
	            String targetUrl = savedRequest.getRedirectUrl();
	            redirectStratgy.sendRedirect(request, response, targetUrl);
	        } else {
	            redirectStratgy.sendRedirect(request, response, "/board/listPageSearch?num=1");
	        }
	        
	    }
	 
	 public String getStudent_id() {
			return student_id;
		}

		public void setStudent_id(String student_id) {
			this.student_id = student_id;
		}

		public String getDefaultUrl() {
			return defaultUrl;
		}

		public void setDefaultUrl(String defaultUrl) {
			this.defaultUrl = defaultUrl;
		}


}

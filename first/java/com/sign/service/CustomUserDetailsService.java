package com.sign.service;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sign.dao.UserAuthDAO;
import com.sign.domain.CustomUserDetails;

public class CustomUserDetailsService implements UserDetailsService{
	@Inject
	private UserAuthDAO userAuthDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("CustomUserDetailsService");
		CustomUserDetails user = userAuthDAO.getUserById(username);
		if(user == null) {
			System.out.println("UserLoginError");
			throw new UsernameNotFoundException(username);
		}
		return user;
	}
	
}

package com.sign.service;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sign.dao.SignDAO;

@Service
public class SignServiceImpl implements SignService {

	@Inject
	private SignDAO dao;
	@Override
	public boolean sign_up(Map<String, Object> paramMap) {
		return dao.sign_up(paramMap);
	}
	
	@Override
	public boolean signedCheck(String student_id) {
		return dao.signedCheck(student_id);
	}

}

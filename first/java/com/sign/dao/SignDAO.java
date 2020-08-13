package com.sign.dao;

import java.util.Map;

public interface SignDAO {
	
	//회원 가입
	public boolean sign_up(Map<String,Object> paramMap);
	
	//회원 확인
	public boolean signedCheck(String student_id);
}

package com.sign.dao;

import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class SignDAOImpl implements SignDAO{
	@Inject
	private SqlSession sql;

	private static String namespace = "com.sign.mappers.sign";
	
	@Override
	public boolean sign_up(Map<String, Object> paramMap) {
		int result = sql.update(namespace+".signUp",paramMap);
		return result > 0 ? true : false;
	}

	@Override
	public boolean signedCheck(String student_id) {
		/* System.out.println(student_id); */
		String result = sql.selectOne("signedCheck", student_id);
		/* System.out.println("result: " + result); */
		if(result == null) {
			System.out.println("null");
			return false;
		}
		return result.equals("1234") ? true : false;
	}

	@Override
	public String selectName(String student_id) {
		return sql.selectOne(namespace+".selectUserName", student_id);
	}

}

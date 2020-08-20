package com.sign.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.sign.domain.CustomUserDetails;

@Repository("userAuthDAO")
public class UserAuthDAO {
	
	@Inject
	private SqlSession sql;
	
	public CustomUserDetails getUserById(String username) {
		return sql.selectOne("com.sign.mappers.sign" +".selectUserById",username);
	}
}

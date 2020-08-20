package com.sign.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

	/*CREATE TABLE student_info(
			student_id VARCHAR(50) NOT NULL,
			student_name VARCHAR(50) NOT NULL,
			student_password VARCHAR(50),
			PRIMARY KEY(student_id)
		)ENGINE=InnoDB DEFAULT CHARSET=utf8;
	*/
	
	private String student_id;
	private String student_name;
	private String student_password;
	private String auth;
	private boolean enabled;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> authority = new ArrayList<>();
		authority.add(new SimpleGrantedAuthority(auth));
		return authority;
	}
	@Override
	public String getPassword() {
		return student_password;
	}
	@Override
	public String getUsername() {
		return student_id;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	
}

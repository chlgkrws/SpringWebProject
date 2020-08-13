package com.sign.domain;

public class StudentVO {
	/*CREATE TABLE student_info(
			student_id VARCHAR(50) NOT NULL,
			student_name VARCHAR(50) NOT NULL,
			student_password VARCHAR(50),
			PRIMARY KEY(student_id)
		)ENGINE=InnoDB DEFAULT CHARSET=utf8;*/

	public String student_id;
	public String student_name;
	public String student_password;
	
	public String getStudent_id() {
		return student_id;
	}
	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public String getStudent_password() {
		return student_password;
	}
	public void setStudent_password(String student_password) {
		this.student_password = student_password;
	}
	
	
}

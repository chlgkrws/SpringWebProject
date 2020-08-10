package com.board.domain;

import java.util.Date;

public class BoardReplyVO {
	/*CREATE TABLE board_reply(
			reply_id INT NOT NULL AUTO_INCREMENT,
			board_bno INT,
			parent_id INT,
			depth INT,
			reply_content TEXT,
			reply_writer VARCHAR(30) NOT NULL,
			reply_password VARCHAR(10) NOT NULL,
			register_datetime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
			PRIMARY KEY(reply_id)
			);
			*/
	
	private String reply_id;
	private String board_bno;
	private String parent_id;
	private String depth;
	private String reply_content;
	public String getReply_id() {
		return reply_id;
	}
	public void setReply_id(String reply_id) {
		this.reply_id = reply_id;
	}
	public String getBoard_bno() {
		return board_bno;
	}
	public void setBoard_bno(String board_bno) {
		this.board_bno = board_bno;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	public String getReply_writer() {
		return reply_writer;
	}
	public void setReply_writer(String reply_writer) {
		this.reply_writer = reply_writer;
	}
	public String getReply_password() {
		return reply_password;
	}
	public void setReply_password(String reply_password) {
		this.reply_password = reply_password;
	}
	public Date getRegister_datetime() {
		return register_datetime;
	}
	public void setRegister_datetime(Date register_datetime) {
		this.register_datetime = register_datetime;
	}
	private String reply_writer;
	private String reply_password;
	private Date register_datetime;
	
}

package com.board.dao;

import java.util.List;
import java.util.Map;

import com.board.domain.BoardReplyVO;
import com.board.domain.BoardVO;

public interface BoardDAO {
	//게시물 목록
	public List<BoardVO> list() throws Exception;
	
	//게시물 작성
	public int write(Map<String, Object> paramMap) throws Exception;
	
	//게시물 조회
	public BoardVO view(int bno) throws Exception;
	
	//게시물 수정
	public void modify(BoardVO vo) throws Exception;
	
	//게시물 삭제
	public void delete(int bno) throws Exception;
	
	//게시물 총 갯수
	public int count() throws Exception;
	
	//게시물 목록 + 페이징
	public List listPage(int displayPost, int postNum) throws Exception;
	
	//게시물 목록 + 페이징 + 검색
	public List<BoardVO> listPageSearch(
			int displayPost, int postNum, String searchType, String keyword) throws Exception;
	
	//게시물 총 갯수 + 검색 적용
	public int searchCount(String searchType, String keyword) throws Exception;
	
	//댓글 리스트
	public List<BoardReplyVO> getReplyList(int bno)throws Exception;
	
	//댓글 등록
	public int regReply(Map<String,Object> paramMap);
	
	//댓글 삭제
	int delReply(Map<String, Object> paramMap);
	
	//댓글 수정
	boolean updateReply(Map<String, Object> paramMap);
	
	//댓글 패스워드 확인
	boolean checkReply(Map<String, Object> paramMap);
}

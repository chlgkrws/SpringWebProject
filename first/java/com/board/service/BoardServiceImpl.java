package com.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.board.dao.BoardDAO;
import com.board.domain.BoardReplyVO;
import com.board.domain.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	private BoardDAO dao;

	// 게시물 목록
	@Override
	public List<BoardVO> list() throws Exception {

		return dao.list();
	}

	// 게시물 작성
	@Override
	public int write(Map<String, Object> paramMap) throws Exception {
		return dao.write(paramMap);
	}

	// 게시물 조회
	@Override
	public BoardVO view(int bno) throws Exception {
		return dao.view(bno);
	}

	// 게시물 수정
	@Override
	public int modify(Map<String, Object>paramMap) throws Exception {
		return dao.modify(paramMap);
	}

	// 게시글 삭제
	@Override
	public void delete(int bno) throws Exception {
		dao.delete(bno);
	}
	// 게시글 카운트
	public int count() throws Exception {
		return dao.count();
	}
	
	//리스트 페이지
	@Override
	public List listPage(int displayPost, int postNum) throws Exception {
		return dao.listPage(displayPost, postNum);
	}
	//리스트 페이지 서치
	@Override
	public List<BoardVO> listPageSearch(int displayPost, int postNum, String searchType, String keyword)
			throws Exception {

		return dao.listPageSearch(displayPost, postNum, searchType, keyword);
	}

	// 게시물 총 갯수
	@Override
	public int searchCount(String searchType, String keyword) throws Exception {
		return dao.searchCount(searchType, keyword);
	}

	// 댓글 리스트
	@Override
	public List<BoardReplyVO> getReplyList(int bno) throws Exception {
		List<BoardReplyVO> boardReplyList = dao.getReplyList(bno);
		

		if (!boardReplyList.isEmpty()) {
			// 부모
			List<BoardReplyVO> boardReplyListParent = new ArrayList<BoardReplyVO>();

			// 자식
			List<BoardReplyVO> boardReplyListChild = new ArrayList<BoardReplyVO>();

			// 통합
			List<BoardReplyVO> newBoardReplyList = new ArrayList<BoardReplyVO>();

			// 부모자식 분리
			for (BoardReplyVO boardReply : boardReplyList) {
				if (boardReply.getDepth().equals("0")) {
					boardReplyListParent.add(boardReply);
				} else {
					boardReplyListChild.add(boardReply);
				}
			}

			for (BoardReplyVO boardReplyParent : boardReplyListParent) {
				newBoardReplyList.add(boardReplyParent);
				for (BoardReplyVO boardReplyChild : boardReplyListChild) {
					if (boardReplyParent.getReply_id().equals(boardReplyChild.getParent_id())) {
						newBoardReplyList.add(boardReplyChild);
					}
				}
			}
			return newBoardReplyList;
		}else {
			return boardReplyList;
		}

	}
	
	//댓글 등록
	@Override
	public int regReply(Map<String, Object> paramMap) {
		
		return dao.regReply(paramMap);
	}

	@Override
	public int delReply(Map<String, Object> paramMap) {
		return dao.delReply(paramMap);
	}

	@Override
	public boolean updateReply(Map<String, Object> paramMap) {
		return dao.updateReply(paramMap);
	}
	
	/* 댓글 패스워드 확인 */
	@Override
    public boolean checkReply(Map<String, Object> paramMap) {
        return dao.checkReply(paramMap);
    }

	@Override
	public void viewCnt(int bno) throws Exception {
		dao.viewCnt(bno);
		
	}

}

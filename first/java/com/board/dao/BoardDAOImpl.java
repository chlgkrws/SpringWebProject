package com.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.board.domain.BoardReplyVO;
import com.board.domain.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO {

	@Inject
	private SqlSession sql;

	private static String namespace = "com.board.mappers.board";

	@Override
	public List<BoardVO> list() throws Exception {
		return sql.selectList(namespace + ".list");
	}

	@Override
	public int write(Map<String, Object> paramMap) throws Exception {
		return sql.insert(namespace + ".write", paramMap);
	}

	@Override
	public BoardVO view(int bno) throws Exception {
		return sql.selectOne(namespace + ".view", bno);
	}

	@Override
	public int modify(Map<String, Object>paramMap) throws Exception {
		return sql.update(namespace + ".modify", paramMap);
	}

	@Override
	public void delete(int bno) throws Exception {
		sql.delete(namespace + ".delete", bno);
	}

	@Override
	public int count() throws Exception {
		return sql.selectOne(namespace + ".count");
	}

	@Override
	public List listPage(int displayPost, int postNum) throws Exception {
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		data.put("displayPost", displayPost);
		data.put("postNum", postNum);

		return sql.selectList(namespace + ".listPage", data);

	}

	@Override
	public List<BoardVO> listPageSearch(int displayPost, int postNum, String searchType, String keyword)
			throws Exception {
		HashMap<String, Object> data = new HashMap<String, Object>();

		data.put("displayPost", displayPost);
		data.put("postNum", postNum);

		data.put("searchType", searchType);
		data.put("keyword", keyword);

		return sql.selectList(namespace + ".listPageSearch", data);
	}

	@Override
	public int searchCount(String searchType, String keyword) throws Exception {

		HashMap<String, Object> data = new HashMap<String, Object>();

		data.put("searchType", searchType);
		data.put("keyword", keyword);

		return sql.selectOne(namespace + ".searchCount", data);
	}

	@Override
	public List<BoardReplyVO> getReplyList(int bno) throws Exception {
		return sql.selectList(namespace + ".boardReplyList", bno);

	}

	@Override
	public int regReply(Map<String, Object> paramMap) {
		return sql.insert(namespace + ".insertBoardReply", paramMap);
	}

	@Override
	public int delReply(Map<String, Object> paramMap) {
		if (paramMap.get("r_type").equals("main")) {
			// 부모부터 하위 다 지움
			return sql.delete("deleteBoardReplyAll", paramMap);
		} else {
			// 자기 자신만 지움
			return sql.delete("deleteBoardReply", paramMap);
		}

	}

	@Override
	public boolean updateReply(Map<String, Object> paramMap) {
		int result = sql.update("updateReply", paramMap);
		return result > 0 ? true : false;
	}

	@Override
	public boolean checkReply(Map<String, Object> paramMap) {
		int result = sql.selectOne("selectReplyStudent_id", paramMap);

		if (result > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void viewCnt(int bno) throws Exception {
		sql.update(namespace+".viewCnt",bno);
	}

}

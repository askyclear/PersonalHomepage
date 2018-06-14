package com.hudini.totalhomepage.dao;

import static com.hudini.totalhomepage.dao.BoardSqls.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.hudini.totalhomepage.dto.BoardDto;
import com.hudini.totalhomepage.dto.CategoryDto;

/*
 * 게시판관련 DataAccessObject
 * 게시판 insert to category
 * 게시판 목록 조회 by categoryId
 * 게시판 글삭제 by index,
 * 생성날짜 : 18.06.06
 * 최종수정날짜 : 18.06.06 
 * 작성자 : 김대선
 */
@Repository
public class BoardDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<BoardDto> boardRowMapper = BeanPropertyRowMapper.newInstance(BoardDto.class);
	private RowMapper<CategoryDto> categoryRowMapper = BeanPropertyRowMapper.newInstance(CategoryDto.class);

	public BoardDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("board").usingGeneratedKeyColumns("id");
	}

	/**
	 * boardCategoryid에 맞는 board 테이블의 데이터들을 return 해 주는 메소드 boardCategoryId가 0
	 * 이라는 것은 전체를 가져오라는 것과 일치한다.
	 * 
	 * @param boardCategoryId
	 *            positive integer type
	 * @param start
	 *            positive integer type
	 * @param limit
	 *            positive integer type
	 * @return
	 */
	public List<BoardDto> selectByBoardCategoryId(int boardCategoryId, int start, int limit) {
		Map<String, Integer> params = new HashMap<>();
		params.put("start", start);
		params.put("limit", limit);
		if (boardCategoryId == 0) {
			return jdbc.query(SELECT_BY_PAGE, params, boardRowMapper);
		} else {
			params.put("boardCategoryId", boardCategoryId);
			return jdbc.query(SELECT_BY_CATEGORY_ID, params, boardRowMapper);
		}
	}

	/**
	 * id 에 맞는 데이터 return
	 * 
	 * @param id
	 *            positive integer type
	 * @return
	 */
	public BoardDto selectById(int id) {
		Map<String, Integer> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(SELECT_BY_ID, params, boardRowMapper);
	}

	/**
	 * 말그대로 insert table
	 * 
	 * @param board
	 * @return
	 */
	public int insert(BoardDto board) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(board);
		return insertAction.execute(params);
	}

	/**
	 * id에 맞는 update table
	 * 
	 * @param
	 * @return
	 */
	public BoardDto update(BoardDto t) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * BoardCateogy Table에서 전체 카테고리 리스트를 가져오는 메소드
	 * 
	 * @return 전체 카테고리 정보를 넘겨줌
	 */
	public List<CategoryDto> selectCategories() {
		return jdbc.query(SELECT_CATEGORIES, categoryRowMapper);
	}

	/**
	 * 전체 게시물의 수를 구하는 메소드
	 * 
	 * @return
	 */
	public int selectCount(int boardCateogryId) {
		
		if (boardCateogryId == 0) {
			return jdbc.queryForObject(SELECT_COUNT, Collections.emptyMap(), Integer.class);
		} else {
			Map<String, Integer> params = new HashMap<>();
			params.put("boardCategoryId", boardCateogryId);
			return jdbc.queryForObject(SELECT_COUNT_BY_CATEGORI_ID, params, Integer.class);
		}
	}

}

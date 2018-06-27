package com.hudini.totalhomepage.dao;
/*
 * 앨범관련 DataAccessObject
 * 앨범 insert to category
 * 앨범 목록 조회 by categoryId
 * 앨범 글삭제 by index,
 * 생성날짜 : 18.06.25
 * 최종수정날짜 : 18.06.25
 * 작성자 : 김대선
 */

import static com.hudini.totalhomepage.dao.BoardSqls.SELECT_BY_CATEGORY_ID;
import static com.hudini.totalhomepage.dao.BoardSqls.SELECT_BY_PAGE;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.hudini.totalhomepage.dto.CategoryDto;
import com.hudini.totalhomepage.dto.PhotoDto;

@Repository
public class AlbumDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<CategoryDto> albumCategoryRowMapper = BeanPropertyRowMapper.newInstance(CategoryDto.class);
	private RowMapper<PhotoDto> albumRowMapper = BeanPropertyRowMapper.newInstance(PhotoDto.class);
	private SimpleJdbcInsert albumInsertAction;
	
	public AlbumDao(DataSource dataSource){
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.albumInsertAction = new SimpleJdbcInsert(dataSource).withTableName("album");
	}
	public List<CategoryDto> selectCategories(){
		return jdbc.query("SELECT id, name FROM album_category", Collections.emptyMap(), albumCategoryRowMapper);
	}
	public List<PhotoDto> selectAlbumByCategoryId(int albumCategoryId, int start, int limit){
		Map<String, Integer> params = new HashMap<>();
		params.put("start", start);
		params.put("limit", limit);
		if (albumCategoryId == 0) {
			return jdbc.query(SELECT_BY_PAGE, params, albumRowMapper);
		} else {
			params.put("boardCategoryId", albumCategoryId);
			return jdbc.query(SELECT_BY_CATEGORY_ID, params, albumRowMapper);
		}
	}
}
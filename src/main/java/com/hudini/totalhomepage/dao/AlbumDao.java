package com.hudini.totalhomepage.dao;
/*
 * 앨범관련 DataAccessObject
 * 앨범 insert to category
 * 앨범 목록 조회 by categoryId
 * 앨범 글삭제 by index,
 * 생성날짜 : 18.06.29
 * 최종수정날짜 : 18.06.29
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
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.hudini.totalhomepage.dto.CategoryDto;
import com.hudini.totalhomepage.dto.PhotoDto;
import com.hudini.totalhomepage.dto.PhotoFileDto;

@Repository
public class AlbumDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<CategoryDto> albumCategoryRowMapper = BeanPropertyRowMapper.newInstance(CategoryDto.class);
	private RowMapper<PhotoDto> albumRowMapper = BeanPropertyRowMapper.newInstance(PhotoDto.class);
	private RowMapper<PhotoFileDto> albumFileRowMapper = BeanPropertyRowMapper.newInstance(PhotoFileDto.class);
	private SimpleJdbcInsert albumInsertAction;
	private SimpleJdbcInsert albumFileInsertAction;
	
	public AlbumDao(DataSource dataSource){
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.albumInsertAction = new SimpleJdbcInsert(dataSource).withTableName("album").usingGeneratedKeyColumns("id");
		this.albumFileInsertAction = new SimpleJdbcInsert(dataSource).withTableName("album_file").usingGeneratedKeyColumns("id");
	}
	
	/**
	 * album 관련 카테고리 리스트를 전부 리턴 
	 * @return 
	 */
	public List<CategoryDto> selectCategories(){
		return jdbc.query("SELECT id, name FROM album_category", Collections.emptyMap(), albumCategoryRowMapper);
	}
	/**
	 * 페이지별 앨범 데이터를 가져오는 메소드
	 * 
	 * @param albumCategoryId albumCategoryId에 포함
	 * @param start positive integer value 전체 리스트에서 가져올 목록 start 값
	 * @param limit positivie integer value start부터 가져올 데이터 양
	 * @return PhotoDto 리스트 리턴
	 */
	public List<PhotoDto> selectAlbumByCategoryId(int albumCategoryId, int start, int limit){
		Map<String, Integer> params = new HashMap<>();
		params.put("start", start);
		params.put("limit", limit);
		if (albumCategoryId == 0) {
			return jdbc.query("SELECT * FROM album ORDER BY modify_date DESC, id DESC LIMIT :start, :limit", params, albumRowMapper);
		} else {
			params.put("albumCategoryId", albumCategoryId);
			return jdbc.query("SELECT * FROM album WHERE album_category_id = :albumCategoryId ORDER BY modify_date DESC, id DESC LIMIT :start, :limit", params, albumRowMapper);
		}
	}
	
	/**
	 * photo 데이터 insert 메소드
	 * @param photo  PhotoDto type
	 * @return primary Key value return
	 */
	public int insert(PhotoDto photo){
		SqlParameterSource params = new BeanPropertySqlParameterSource(photo);
		Number number = albumInsertAction.executeAndReturnKey(params);
		return number.intValue();
	}
	/**
	 * photoFile 데이터 insert 메소드
	 * @param photoFile PhotoDto type
	 * @return
	 */
	public int insertAlbumFile(PhotoFileDto photoFile) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(photoFile);
		Number number = albumFileInsertAction.executeAndReturnKey(params);
		return number.intValue();
		
	}
	/**
	 * album table에서 id 와 일치하는 데이터 1개 가져오기
	 * @param id positive integer album table id
	 * @return
	 */
	public PhotoDto selectById(int id) {
		Map<String, Integer> param = new HashMap<>();
		param.put("id", id);
		
		return jdbc.queryForObject("SELECT * FROM album WHERE id = :id", param, albumRowMapper);
	}
	/**
	 * category에 충족하는 데이터 개수 가져오기
	 * @param categoryId
	 * @return
	 */
	public int selectCount(int categoryId) {
		
		if(categoryId != 0){
			Map<String, Integer> param = new HashMap<>();
			param.put("categoryId", categoryId);
			return jdbc.queryForObject("SELECT COUNT(*) FROM album WHERE album_category_id = :categoryId", param, Integer.class) ;
		}else {
			return jdbc.queryForObject("SELECT COUNT(*) FROM album", Collections.emptyMap(), Integer.class); 
		}
	}
	/**
	 * album file 에서 관련 정보 가져오기
	 * @param id
	 * @return
	 */
	public List<PhotoFileDto> selectAlbumFileByAlbumId(int id) {
		Map<String, Integer> params = new HashMap<>();
		params.put("albumId", id);
		return jdbc.query("SELECT * FROM album_file WHERE album_id = :albumId",params, albumFileRowMapper);
	}
	
}
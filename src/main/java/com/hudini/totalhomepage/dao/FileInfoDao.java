package com.hudini.totalhomepage.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.hudini.totalhomepage.dto.FileInfoDto;

/*
 * file 관련 DataAccessObjectClass
 * 생성날짜 : 2018.06.15 최종수정날짜 : 2018.06.15
 * 작성자 : 김대선
 */
@Repository
public class FileInfoDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insert;
	private RowMapper<FileInfoDto> fileRowMapper = BeanPropertyRowMapper.newInstance(FileInfoDto.class); 
	
	public FileInfoDao(DataSource dataSource){
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insert = new SimpleJdbcInsert(dataSource).withTableName("file_info").usingGeneratedKeyColumns("id");
	}
	/**
	 * id와 일치하는 file_info 
	 * @param id  file_info table id positive integer
	 * @return FileInfoDto or null
	 * @exception EmptyResultDataAccessException 
	 */
	public FileInfoDto selectById(int id){
		try{
			Map<String, Integer> param = new HashMap<>();
			param.put("id", id);
			return jdbc.queryForObject("SELECT id, file_name, save_file_name, content_type, delete_flag, create_date, modify_date FROM file_info WHERE id = :id", param, fileRowMapper);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	public int delete(int id){
		return 0;
	}
	public int update(FileInfoDto fileInfo){
		return 0;
	}
	/**
	 * insert는 SimpleJdbcInsert로 value는 fileInfo를 BeanPropertySqlParmeter로 변환해 넣기
	 * @param fileInfo FileInfoDto 객체
	 * @return
	 */
	public int insert(FileInfoDto fileInfo){
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(fileInfo);
		Number number = insert.executeAndReturnKey(parameterSource);
		return number.intValue();
	}
}

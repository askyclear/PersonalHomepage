package com.hudini.totalhomepage.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.hudini.totalhomepage.dto.UserDto;
import static com.hudini.totalhomepage.dao.UserSqls.*;
/*
 * User 관련 Data Access Object 
 * 작성날짜 : 2018.06.11 최종수정날짜 : 2018.06.13
 * 작성자 : 김대선
 */
@Repository
public class UserDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<UserDto> rowMapper = BeanPropertyRowMapper.newInstance(UserDto.class);
	private SimpleJdbcInsert insertAction;

	public UserDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("user").usingGeneratedKeyColumns("id");
	}

	/**
	 * userId와 같은 데이터의 수를 리턴
	 * 
	 * @param userId
	 * @return 없으면 0 잇으면 1을 리턴
	 */
	public int selectCountByUserId(String userId) {
		Map<String, String> params = new HashMap<>();
		params.put("userId", userId);
		return jdbc.queryForObject(SELECT_COUNT_BY_USER_ID, params, Integer.class);
	}

	/**
	 * userId 값을 가진 UserDto 객체 반환
	 * 
	 * @param userId
	 * @return 있으면 UserDto 객체를 없으면 null 반환
	 */
	public UserDto selectByUserId(String userId) {
		Map<String, String> params = new HashMap<>();
		params.put("userId", userId);
		return jdbc.queryForObject(SELECT_BY_USER_ID, params,rowMapper);
	}

	/**
	 * nickname이 같은 count 리턴
	 * 
	 * @param nickName
	 * @return 없으면 0 있으면 1 이상
	 */
	public int selectCountByNickName(String nickName) {
		Map<String, String> params = new HashMap<>();
		params.put("nickname", nickName);
		return jdbc.queryForObject(SELECT_COUNT_BY_NICKNAME, params, Integer.class);
	}
	
	public int update(UserDto user){
		return 0;
	}
	/**
	 * user 테이블에 입력받은 UserDto객체와 맵핑되는 데이터 insert
	 * 
	 * @param user
	 * @return 성공시 1 반환
	 */
	public int insert(UserDto user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		return insertAction.execute(param);
	}

	public int selectCountByUserIdAndPassword(String userId, String password) {
		Map<String, String> params = new HashMap<>();
		params.put("userId", userId);
		params.put("password", password);
		return jdbc.queryForObject(SELECTCOUNT_BY_USERIDANDPASSWORD, params, Integer.class);
	}

	public UserDto selectById(int userId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("id", userId);
		return jdbc.queryForObject(SELECT_BY_ID, params,rowMapper);
	}
}

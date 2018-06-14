package com.hudini.totalhomepage.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudini.totalhomepage.dao.UserDao;
import com.hudini.totalhomepage.dto.UserDto;
import com.hudini.totalhomepage.service.UserService;
/*
 * User관련 서비스 객체
 * 
 * 작성날짜 : 2018.06.11 최종수정날짜 : 2018.06.14
 * 작성자 : 김대선
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;
	/**
	 * userId와 일치하는 usertable의 count를 리턴함
	 * @return success 1 others fail
	 */
	@Override
	public int checkDuplicationUser(String userId) {
		
		return userDao.selectCountByUserId(userId);
	}
	/**
	 * nickname과 일치하는 user table의 count를 리턴함
	 * @return success 1 others fail
	 */
	@Override
	public int checkDuplicationNickName(String nickname) {
		
		return userDao.selectCountByNickName(nickname);
	}
	
	/**
	 * user 테이블에 user 정보 추가
	 * @return success 1 others fail
	 */
	@Override
	public int addUser(UserDto user) {
		user.setCreateDate(new Date());
		user.setLastLoginDate(new Date());
		user.setUserTypeId(2);
		return userDao.insert(user);
	}
	
	/**
	 * id와 password와 일치하는
	 * @return success 1 others fail 
	 */
	@Override
	public int checkUser(String userId, String password) {
		return userDao.selectCountByUserIdAndPassword(userId, password);
	}
	
	/**
	 * userId와 password가 일치하는 user 정보 가져오기
	 * 
	 */
	@Override
	public UserDto getUser(String userId, String password) {
		
		return userDao.selectByUserId(userId);
	}
	
	
}

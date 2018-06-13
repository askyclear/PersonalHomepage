package com.hudini.totalhomepage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudini.totalhomepage.dao.UserDao;
import com.hudini.totalhomepage.service.UserService;
/*
 * User관련 서비스 객체
 * 
 * 작성날짜 : 2018.06.11 최종수정날짜 : 2018.06.11
 * 작성자 : 김대선
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;

	@Override
	public int checkDuplicationUser(String userId) {
		
		return userDao.selectCountByUserId(userId);
	}

	@Override
	public int checkDuplicationNickName(String nickname) {
		
		return userDao.selectCountByNickName(nickname);
	}
	
}

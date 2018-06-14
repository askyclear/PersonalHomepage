package com.hudini.totalhomepage.service;

import com.hudini.totalhomepage.dto.UserDto;

/*
 * 작성날짜 : 2018.06.11 최종수정날짜 : 2018.06.14
 * 작성자 : 김대선
 */
public interface UserService {
	public int checkDuplicationUser(String userId);
	public int checkDuplicationNickName(String nickname);
	public int addUser(UserDto user);
	public int checkUser(String id, String password);
	public UserDto getUser(String userId, String password);
}

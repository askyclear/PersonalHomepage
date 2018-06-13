package com.hudini.totalhomepage.service;
/*
 * 작성날짜 : 2018.06.11 최종수정날짜 : 2018.06.11
 * 작성자 : 김대선
 */
public interface UserService {
	public int checkDuplicationUser(String userId);
	public int checkDuplicationNickName(String nickname);
}

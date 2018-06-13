package com.hudini.totalhomepage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hudini.totalhomepage.service.UserService;

/*
 * 회원가입과 관련된 api Controller 클래스
 * 작성날짜 : 2018.06.11 최종수정날짜 : 2018.06.11
 * 작성자 : 김대선
 */
@RestController
@RequestMapping("/api/signup")
public class SignUpApiController {
	@Autowired
	UserService userService;

	@RequestMapping(path = {"/duplicationId.do"})
	public String duplicationId(HttpServletRequest request, Model model){
		String userId = "";
		userId = request.getParameter("userId");
		if(userId == null || userId.equals(""))
			return "false";
		int userCount = userService.checkDuplicationUser(userId);
		if(userCount != 0) {
			return "false";
		}
		else {
			return "ok";
		}
	}
	@RequestMapping(path = {"/duplicationNickName.do"})
	public String duplicationNickName(HttpServletRequest request, Model model){
		String nickname = "";
		nickname = request.getParameter("nickname");
		if(nickname == null || nickname.equals(""))
			return "false";
		int nicknameCount = userService.checkDuplicationNickName(nickname);
		if(nicknameCount != 0){
			return "false";
		}
		else {
			return "ok";
		}
	}
}

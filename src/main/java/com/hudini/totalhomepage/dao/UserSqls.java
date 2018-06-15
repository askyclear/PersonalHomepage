package com.hudini.totalhomepage.dao;

public class UserSqls {
	public static final String SELECT_BY_USER_ID = "SELECT * FROM user WHERE user_id = :userId";
	public static final String SELECT_COUNT_BY_USER_ID = "SELECT COUNT(*) FROM USER WHERE user_id = :userId";
	public static final String SELECT_COUNT_BY_NICKNAME = "SELECT COUNT(*) FROM USER WHERE nickname = :nickname";
	public static final String SELECT_BY_ID = "SELECT * FROM user WHERE id = :id";
	public static final String SELECTCOUNT_BY_USERIDANDPASSWORD ="SELECT COUNT(*) FROM user WHERE user_id = :userId AND password = :password";
}

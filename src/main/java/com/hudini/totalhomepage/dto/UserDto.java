package com.hudini.totalhomepage.dto;

import java.util.Date;

public class UserDto {
	private int id;
	private String userId;
	private String password;
	private String nickname;
	private int userTypeId;
	private Date createDate;
	private Date lastLoginDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", userId=" + userId + ", password=" + password + ", nickname=" + nickname
				+ ", userTypeId=" + userTypeId + ", createDate=" + createDate + ", lastLoginDate=" + lastLoginDate
				+ "]";
	}
}

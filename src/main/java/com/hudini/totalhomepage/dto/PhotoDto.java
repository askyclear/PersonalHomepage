package com.hudini.totalhomepage.dto;

import java.util.Date;
/*
 * album DataTranferObject
 * 생성날짜 : 18.06.25
 * 최종수정날짜 : 18.06.25 
 * 작성자 : 김대선
 */
public class PhotoDto {
	private int id;
	private int albumCategoryId;
	private int userId;
	private String title;
	private String content;
	private Date createDate;
	private Date modifyDate;
	private int viewCount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAlbumCategoryId() {
		return albumCategoryId;
	}
	public void setAlbumCategoryId(int albumCategoryId) {
		this.albumCategoryId = albumCategoryId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	@Override
	public String toString() {
		return "PhotoDto [id=" + id + ", albumCategoryId=" + albumCategoryId + ", userId=" + userId + ", title=" + title
				+ ", content=" + content + ", createDate=" + createDate + ", modifyDate=" + modifyDate + ", viewCount="
				+ viewCount + "]";
	}
	
	
}

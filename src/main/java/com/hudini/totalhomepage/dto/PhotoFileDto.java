package com.hudini.totalhomepage.dto;
/*
 * 앨범에 업로드된 이미지에 대한 정보
 * 작성 날짜 : 2018.06.29  최종수정날짜 : 2018.06.29
 * 작성자 : 김대선
 */
public class PhotoFileDto {
	private int id;
	private int albumId;
	private String type;
	private int fileId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAlbumId() {
		return albumId;
	}
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	
	
}

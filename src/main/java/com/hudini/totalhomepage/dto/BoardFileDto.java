package com.hudini.totalhomepage.dto;
/*
 * 게시판에 업로드된 파일에 대한 정보
 * 작성 날짜 : 2018.06.15  최종수정날짜 : 2018.06.15
 * 작성자 : 김대선
 */
public class BoardFileDto {
	private int id;
	private int boardId;
	private String type;
	private int fileId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
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
	@Override
	public String toString() {
		return "BoardFileDto [id=" + id + ", boardId=" + boardId + ", type=" + type + ", fileId=" + fileId + "]";
	}
	
	
}

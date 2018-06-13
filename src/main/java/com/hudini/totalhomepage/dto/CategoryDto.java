package com.hudini.totalhomepage.dto;
/*
 * 게시판과 앨범의 DataTransferObject
 * 작성 날짜 : 2018.06.08  최종수정날짜 : 2018.06.08
 * 작성자 : 김대선
 */
public class CategoryDto {
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

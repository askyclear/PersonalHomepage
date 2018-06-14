package com.hudini.totalhomepage.service;

import java.util.List;

import com.hudini.totalhomepage.dto.CategoryDto;

public interface BoardService <T>{
	public List<CategoryDto> readCategories();
	public List<T> readList(int categoryId, int start);
	public int write(T boardDto);
	public T modify(T boardDto);
	public T read(int id);
	public int pageCount(int categoryId);
}

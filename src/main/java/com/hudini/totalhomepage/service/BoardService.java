package com.hudini.totalhomepage.service;

import java.util.List;

import com.hudini.totalhomepage.dto.CategoryDto;
import com.hudini.totalhomepage.dto.FileInfoDto;

public interface BoardService <T>{
	public List<CategoryDto> readCategories();
	public List<T> readList(int categoryId, int start);
	public int write(T boardDto, int fileId);
	public T read(int id);
	public int pageCount(int categoryId);
	public int addFile(FileInfoDto fileInfo);
	public List<FileInfoDto> readFiles(int id);
	public FileInfoDto readFile(int boardId, String fileName);
	public int viewCountPlus(int id);
	public T modify(T t, int fileId);
}

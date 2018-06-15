package com.hudini.totalhomepage.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hudini.totalhomepage.dto.CategoryDto;
import com.hudini.totalhomepage.dto.FileInfoDto;
import com.hudini.totalhomepage.dto.PhotoDto;
import com.hudini.totalhomepage.service.BoardService;

@Service("photoService")
public class PhotoServiceImpl implements BoardService<PhotoDto> {

	@Override
	public List<CategoryDto> readCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PhotoDto> readList(int categoryId, int start) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int write(PhotoDto boardDto, int fileId) {
		// TODO Auto-generated method stub
		return 6666;
	}

	@Override
	public PhotoDto modify(PhotoDto boardDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PhotoDto read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int pageCount(int categoryId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addFile(FileInfoDto fileInfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}

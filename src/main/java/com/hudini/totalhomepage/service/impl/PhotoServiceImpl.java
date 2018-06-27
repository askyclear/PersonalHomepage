package com.hudini.totalhomepage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudini.totalhomepage.dao.AlbumDao;
import com.hudini.totalhomepage.dto.CategoryDto;
import com.hudini.totalhomepage.dto.FileInfoDto;
import com.hudini.totalhomepage.dto.PhotoDto;
import com.hudini.totalhomepage.service.BoardService;

/*
 * 게시판 글 작성 Service
 * 작성날짜 : 2018.06.08   최종수정날짜 : 2018.06.18
 * 작성자 : 김대선
 */
@Service("photoService")
public class PhotoServiceImpl implements BoardService<PhotoDto> {
	@Autowired
	AlbumDao albumDao;
	@Override
	public List<CategoryDto> readCategories() {
		CategoryDto categoryZero = new CategoryDto();
		categoryZero.setId(0);
		categoryZero.setName("전체보기");
		List<CategoryDto> categories = albumDao.selectCategories();
		categories.add(0,categoryZero);
		return categories;
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

	@Override
	public List<FileInfoDto> readFiles(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileInfoDto readFile(int boardId, String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int viewCountPlus(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

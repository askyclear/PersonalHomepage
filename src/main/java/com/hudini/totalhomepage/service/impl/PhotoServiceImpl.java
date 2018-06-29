package com.hudini.totalhomepage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hudini.totalhomepage.dao.AlbumDao;
import com.hudini.totalhomepage.dao.FileInfoDao;
import com.hudini.totalhomepage.dao.UserDao;
import com.hudini.totalhomepage.dto.BoardDto;
import com.hudini.totalhomepage.dto.BoardFileDto;
import com.hudini.totalhomepage.dto.CategoryDto;
import com.hudini.totalhomepage.dto.FileInfoDto;
import com.hudini.totalhomepage.dto.PhotoDto;
import com.hudini.totalhomepage.dto.PhotoFileDto;
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
	@Autowired
	UserDao userDao;
	@Autowired
	FileInfoDao fileDao;
	private static final int LIMIT = 10;
	
	/**
	 * Album탭의 카테고리 리스트를 불러오는 메소드로 id 0 은 전체보기이다.
	 */
	@Override
	public List<CategoryDto> readCategories() {
		CategoryDto categoryZero = new CategoryDto();
		categoryZero.setId(0);
		categoryZero.setName("전체보기");
		List<CategoryDto> categories = albumDao.selectCategories();
		categories.add(0,categoryZero);
		return categories;
	}
	/**
	 * 앨범 리스트를 가져오는데 category id 별로 가져오는 것이 다름. pageNumber 별로 다름
	 * 
	 */
	@Override
	public List<PhotoDto> readList(int categoryId, int pageNumber) {
		 int start = (pageNumber - 1) * LIMIT;
		List<PhotoDto> albumList = albumDao.selectAlbumByCategoryId(categoryId, start, LIMIT);
		for (PhotoDto album : albumList) {
			int userId = album.getUserId();
			album.setUserInfo(userDao.selectById(userId));
			FileInfoDto file = readFiles(album.getId()).get(0);
			album.setSaveFileName(file.getSaveFileName());
		}
		return albumList;
	}
	
	/**
	 * 앨범 등록으로 fileId는 무조건 0이면 안됨. 이미지가 존재해야함
	 * 
	 */
	@Transactional
	@Override
	public int write(PhotoDto photoDto, int fileId) {
		if (photoDto.getUserId() == 0) {
			photoDto.setUserId(1);
		}
		photoDto.setCreateDate(new Date());
		photoDto.setModifyDate(new Date());
		int photoId = albumDao.insert(photoDto);
		if (fileId != 0) {
			PhotoFileDto photoFile = new PhotoFileDto();
			photoFile.setFileId(fileId);
			photoFile.setAlbumId(photoId);
			photoFile.setType("th");
			albumDao.insertAlbumFile(photoFile);
		}
		return photoId;
	}

	@Override
	public PhotoDto modify(PhotoDto boardDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PhotoDto read(int id) {
		PhotoDto album = albumDao.selectById(id);
		int userId = album.getUserId();
		album.setUserInfo(userDao.selectById(userId));
		return album;
	}
	
	@Override
	public int pageCount(int categoryId) {
		int albumCount = albumDao.selectCount(categoryId);
		int pageCount = albumCount / LIMIT;
		if (albumCount % LIMIT == 0) {
			return pageCount;
		} else {
			return pageCount + 1;
		}
		
	}

	@Override
	public int addFile(FileInfoDto fileInfo) {
		fileInfo.setCreateDate(new Date());
		fileInfo.setModifyDate(new Date());
		return fileDao.insert(fileInfo);
	}

	@Override
	public List<FileInfoDto> readFiles(int id) {
		 List<PhotoFileDto> album_files = albumDao.selectAlbumFileByAlbumId(id);
		List<FileInfoDto> file_infos = new ArrayList<>(); 
		for(PhotoFileDto albumFileDto : album_files){
			int file_id = albumFileDto.getFileId();
			file_infos.add(fileDao.selectById(file_id));
		}
		System.out.println(album_files.size());
		return file_infos;
	}

	@Override
	public FileInfoDto readFile(int id, String fileName) {
		List<PhotoFileDto> album_files = albumDao.selectAlbumFileByAlbumId(id);
		
		for(PhotoFileDto albumFileDto : album_files){
			int file_id = albumFileDto.getFileId();
			FileInfoDto file = fileDao.selectById(file_id);
			if(file.getFileName().equals(fileName)){
				return file;
			}
		}
		return null;
	}

	@Override
	public int viewCountPlus(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

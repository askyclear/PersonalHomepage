package com.hudini.totalhomepage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hudini.totalhomepage.dao.BoardDao;
import com.hudini.totalhomepage.dao.FileInfoDao;
import com.hudini.totalhomepage.dao.UserDao;
import com.hudini.totalhomepage.dto.BoardDto;
import com.hudini.totalhomepage.dto.BoardFileDto;
import com.hudini.totalhomepage.dto.CategoryDto;
import com.hudini.totalhomepage.dto.FileInfoDto;
import com.hudini.totalhomepage.service.BoardService;

/*
 * 게시판 글 작성 Service
 * 작성날짜 : 2018.06.08   최종수정날짜 : 2018.06.18
 * 작성자 : 김대선
 */
@Service("postService")
public class PostServiceImpl implements BoardService<BoardDto> {
	@Autowired
	BoardDao boardDao;
	@Autowired
	UserDao userDao;
	@Autowired
	FileInfoDao fileDao;
	private static final int LIMIT = 20;

	/**
	 * categoryId에 걸맞는 게시판 list 조회 pageNum
	 */
	@Override
	public List<BoardDto> readList(int categoryId, int pageNumber) {
		int start = (pageNumber - 1) * LIMIT;
		List<BoardDto> boardList = boardDao.selectByBoardCategoryId(categoryId, start, LIMIT);
		for (BoardDto board : boardList) {
			int userId = board.getUserId();
			board.setUserInfo(userDao.selectById(userId));
		}
		return boardList;
	}

	/**
	 * 글쓰기 서비스
	 * 
	 * @param userId
	 *            userId positive Integer type,
	 * @return success 1 return
	 */
	@Transactional
	@Override
	public int write(BoardDto t, int fileId) {
		if (t.getUserId() == 0) {
			t.setUserId(1);
		}
		t.setCreateDate(new Date());
		t.setModifyDate(new Date());
		int boardId = boardDao.insert(t);
		if (fileId != 0) {
			BoardFileDto boardFile = new BoardFileDto();
			boardFile.setFileId(fileId);
			boardFile.setBoardId(boardId);
			boardFile.setType("fi");
			boardDao.insertBoardFile(boardFile);
		}
		return boardId;
	}

	/**
	 * 게시글 불러오기
	 * 
	 * @param id
	 *            positive integer
	 */
	@Override
	public BoardDto read(int id) {
		BoardDto board = boardDao.selectById(id);
		int userId = board.getUserId();
		board.setUserInfo(userDao.selectById(userId));
		return board;
	}

	/**
	 * 게시글 수정
	 * 
	 * @param t
	 */
	@Override
	@Transactional
	public BoardDto modify(BoardDto t) {
		int result = boardDao.update(t);
		BoardDto board = new BoardDto();
		if(result >= 1){
			board = boardDao.selectById(t.getId());
		}
		return board;
	}

	/**
	 * 전체 카테고리를 읽어옴
	 */
	@Override
	public List<CategoryDto> readCategories() {
		CategoryDto category = new CategoryDto();
		category.setId(0);
		category.setName("전체보기");
		List<CategoryDto> categories = boardDao.selectCategories();
		categories.add(0, category);

		return categories;
	}

	@Override
	public int pageCount(int boardCategoryId) {
		int boardCount = boardDao.selectCount(boardCategoryId);
		int pageCount = boardCount / LIMIT;
		if (boardCount % LIMIT == 0) {
			return pageCount;
		} else {
			return pageCount + 1;
		}
	}
	@Transactional
	@Override
	public int addFile(FileInfoDto fileInfo) {
		fileInfo.setCreateDate(new Date());
		fileInfo.setModifyDate(new Date());
		return fileDao.insert(fileInfo);
	}
	
	@Override
	public List<FileInfoDto> readFiles(int id) {
		List<BoardFileDto> board_files = boardDao.selectBoardFileByBoardId(id);
		List<FileInfoDto> file_infos = new ArrayList<>(); 
		for(BoardFileDto boardFileDto : board_files){
			int file_id = boardFileDto.getFileId();
			file_infos.add(fileDao.selectById(file_id));
		}
		return file_infos;
	}

	@Override
	public FileInfoDto readFile(int boardId, String fileName) {
		List<BoardFileDto> board_files = boardDao.selectBoardFileByBoardId(boardId);
		 
		for(BoardFileDto boardFileDto : board_files){
			int file_id = boardFileDto.getFileId();
			FileInfoDto file_info =fileDao.selectById(file_id);
			if(file_info.getFileName().equals(fileName)){
				return file_info;
			}
		}
		return null;
	}

	@Override
	public int viewCountPlus(int id) {
		
		return boardDao.updateViewCountById(id);
	}
	
}

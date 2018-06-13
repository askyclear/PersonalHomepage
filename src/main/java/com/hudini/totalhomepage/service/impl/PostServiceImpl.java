package com.hudini.totalhomepage.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudini.totalhomepage.dao.BoardDao;
import com.hudini.totalhomepage.dto.BoardDto;
import com.hudini.totalhomepage.dto.CategoryDto;
import com.hudini.totalhomepage.service.BoardService;
/*
 * 게시판 글 작성 Service
 * 작성날짜 : 2018.06.08   최종수정날짜 : 2018.06.08
 * 작성자 : 김대선
 */
@Service("postService")
public class PostServiceImpl implements BoardService<BoardDto> {
	@Autowired
	BoardDao boardDao;
	private static final int LIMIT = 20;
	/**
	 * categoryId에 걸맞는 게시판 list 조회 pageNum
	 */
	@Override
	public List<BoardDto> readList(int categoryId, int pageNumber) {
		int start = (pageNumber - 1) * 20;
		List<BoardDto> boardList = boardDao.selectByBoardCategoryId(categoryId, start, LIMIT);
		return boardList;
	}
	/**
	 * 글쓰기 서비스
	 * @param userId userId positive Integer type,
	 * @return success 1 return 
	 */
	@Override
	public int write(BoardDto t) {
		if(t.getUserId() == 0){
			t.setUserId(1);
		}
		t.setCreateDate(new Date());
		t.setModifyDate(new Date());
		int result = boardDao.insert(t);
		return result;
	}
	/**
	 * 게시글 불러오기
	 * @param id positive integer
	 */
	@Override
	public BoardDto read(int id) {
		BoardDto board = boardDao.selectById(id);
		return board;
	}
	/**
	 * 게시글 수정
	 * @param t 
	 */
	@Override
	public BoardDto modify(BoardDto t) {
		BoardDto board = boardDao.update(t);
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
		if(boardCount % LIMIT == 0) {
			return pageCount;
		}
		else {
			return pageCount + 1;
		}
	}
}

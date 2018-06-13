package com.hudini.totalhomepage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hudini.totalhomepage.dto.BoardDto;
import com.hudini.totalhomepage.dto.CategoryDto;
import com.hudini.totalhomepage.service.BoardService;
/*
 * 게시판 Controller 요청에 따른 view를 리턴해줌
 * 생성날짜 : 18.06.06
 * 최종수정날짜 : 18.06.07
 * 작성자 : 김대선
 */

@Controller
@RequestMapping(path = "/board")
public class BoardController {
	@Autowired
	BoardService<BoardDto> postService;
	
	@RequestMapping(path = {"/write"})
	public ModelAndView writeForm(@RequestParam(name="boardCegoryId", defaultValue="1") int boardCategoryId,
							ModelAndView modelAndView){
		System.out.println("boardCategory id : " + boardCategoryId);
		List<CategoryDto> categories = postService.readCategories();
		modelAndView.addObject("categories", categories);
		modelAndView.addObject("curCategory", boardCategoryId);
		modelAndView.setViewName("writeForm");
		return modelAndView;
	}
	
	@PostMapping(path = {"/write.do"})
	public String write(@ModelAttribute BoardDto board){
		System.out.println(board.toString());
		int result = postService.write(board);
		if(result == 1){
			return "redirect:../board?boardCategoryId=" + board.getBoardCategoryId();
		}else{
			return "redirect:../board?boardCategoryId=" + board.getBoardCategoryId();
		}
	}
	@RequestMapping(path = {"/read.do"})
	public ModelAndView read(@RequestParam(name="boardIndex", required=true) int id,
								@RequestParam(name="boardCegoryId") int boardCegoryId,
								ModelAndView modelAndView){
		BoardDto board = postService.read(id);
		List<CategoryDto> categories = postService.readCategories();
		modelAndView.addObject("categories", categories);
		modelAndView.addObject("preCategoryId", boardCegoryId);
		modelAndView.addObject("board", board);
		modelAndView.setViewName("readPost");
		return modelAndView;
	}
}

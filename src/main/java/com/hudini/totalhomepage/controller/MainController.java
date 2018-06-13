package com.hudini.totalhomepage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hudini.totalhomepage.dto.BoardDto;
import com.hudini.totalhomepage.dto.CategoryDto;
import com.hudini.totalhomepage.dto.PhotoDto;
import com.hudini.totalhomepage.service.BoardService;

/*
 * Main View관련 Controller
 * 생성날짜 : 18.06.07
 * 최종수정날짜 : 18.06.11 
 * 작성자 : 김대선
 */
@Controller
public class MainController {
	@Autowired
	BoardService<BoardDto> postService;
	
	@Autowired
	BoardService<PhotoDto> photoService;
	
	
	@RequestMapping(path = {"/main"})
	public ModelAndView main(ModelAndView modelAndView){
		List<CategoryDto> boardCategories = postService.readCategories();
		modelAndView.addObject("categories", boardCategories);
		modelAndView.setViewName("main");
		
		return modelAndView;
	}
	@RequestMapping(path = {"/board"})
	public ModelAndView board(@RequestParam(name="boardCategoryId",defaultValue="0") int boardCategoryId,
								@RequestParam(name="page", defaultValue="1") int pageNumber,
								ModelAndView modelAndView){
		
		List<BoardDto> boards = postService.readList(boardCategoryId, pageNumber);
		List<CategoryDto> boardCategories = postService.readCategories();
		int pageCount = postService.pageCount(boardCategoryId);
		modelAndView.addObject("boardCategoryId", boardCategoryId);
		modelAndView.addObject("boards", boards);
		modelAndView.addObject("categories", boardCategories);
		modelAndView.addObject("pageCount",pageCount);
		modelAndView.addObject("curPage",pageNumber);
		modelAndView.setViewName("post");
		return modelAndView;
	}
	
	@RequestMapping(path = {"/album"})
	public ModelAndView album(@RequestParam(name="albumCategoryId",defaultValue="0") int albumCategoryId,
								@RequestParam(name="page", defaultValue="1") int pageNumber,
									ModelAndView modelAndView){
		List<CategoryDto> boardCategories = postService.readCategories();
		modelAndView.addObject("categories", boardCategories);
		modelAndView.addObject("albumCategoryId", albumCategoryId);
		modelAndView.setViewName("photo");
		return modelAndView;
	}
	@RequestMapping(path = {"/login"})
	public ModelAndView login(ModelAndView modelAndView){
		List<CategoryDto> boardCategories = postService.readCategories();
		modelAndView.addObject("categories", boardCategories);
		modelAndView.setViewName("loginForm");
		return modelAndView;
	}
	@RequestMapping(path = {"/signup"})
	public ModelAndView signUp(ModelAndView modelAndView){
		modelAndView.setViewName("signupForm");
		return modelAndView;
	}
	@RequestMapping(path = {"/setting"})
	public ModelAndView setting(ModelAndView modelAndView){
		List<CategoryDto> boardCategories = postService.readCategories();
		modelAndView.addObject("categories", boardCategories);
		modelAndView.setViewName("setting");
		return modelAndView;
	}
}

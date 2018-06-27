package com.hudini.totalhomepage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hudini.totalhomepage.dto.BoardDto;
import com.hudini.totalhomepage.dto.CategoryDto;
import com.hudini.totalhomepage.dto.PhotoDto;
import com.hudini.totalhomepage.dto.UserDto;
import com.hudini.totalhomepage.service.BoardService;
import com.hudini.totalhomepage.service.UserService;

/*
 * Main View관련 Controller
 * 생성날짜 : 18.06.07
 * 최종수정날짜 : 18.06.25 
 * 작성자 : 김대선
 */
@Controller
public class MainController {
	@Autowired
	BoardService<BoardDto> postService;
	
	@Autowired
	BoardService<PhotoDto> photoService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(path = {"/main","/"})
	public ModelAndView main(ModelAndView modelAndView){
		List<CategoryDto> boardCategories = postService.readCategories();
		List<CategoryDto> albumCategories = photoService.readCategories();
		modelAndView.addObject("boardcategories", boardCategories);
		modelAndView.addObject("albumcategories",albumCategories);
		modelAndView.setViewName("main");
		
		return modelAndView;
	}
	@RequestMapping(path = {"/board"})
	public ModelAndView board(@RequestParam(name="boardCategoryId",defaultValue="0") int boardCategoryId,
								@RequestParam(name="page", defaultValue="1") int pageNumber,
								ModelAndView modelAndView){
		
		List<BoardDto> boards = postService.readList(boardCategoryId, pageNumber);
		List<CategoryDto> boardCategories = postService.readCategories();
		List<CategoryDto> albumCategories = photoService.readCategories();
		int pageCount = postService.pageCount(boardCategoryId);
		modelAndView.addObject("boardCategoryId", boardCategoryId);
		modelAndView.addObject("boards", boards);
		modelAndView.addObject("boardcategories", boardCategories);
		modelAndView.addObject("albumcategories",albumCategories);
		modelAndView.addObject("pageCount",pageCount);
		modelAndView.addObject("curPage",pageNumber);
		modelAndView.setViewName("post");
		return modelAndView;
	}
	
	@RequestMapping(path = {"/album"})
	public ModelAndView album(@RequestParam(name="albumCategoryId",defaultValue="0") int albumCategoryId,
								@RequestParam(name="page", defaultValue="1") int pageNumber,
									ModelAndView modelAndView){
		List<PhotoDto> photos = photoService.readList(albumCategoryId, pageNumber);
		List<CategoryDto> boardCategories = postService.readCategories();
		List<CategoryDto> albumCategories = photoService.readCategories();
		int pageCount = photoService.pageCount(albumCategoryId);
		modelAndView.addObject("albumCategoryId",albumCategoryId);
		modelAndView.addObject("photos", photos);
		modelAndView.addObject("boardcategories", boardCategories);
		modelAndView.addObject("albumcategories", albumCategories);
		modelAndView.addObject("albumCategoryId", albumCategoryId);
		modelAndView.addObject("pageCount",pageCount);
		modelAndView.addObject("curPage",pageNumber);
		modelAndView.setViewName("photo");
		return modelAndView;
	}
	/**
	 * 로그인폼을 호출함
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(path = {"/login"})
	public ModelAndView loginForm(ModelAndView modelAndView){
		List<CategoryDto> boardCategories = postService.readCategories();
		List<CategoryDto> albumCategories = photoService.readCategories();
		modelAndView.addObject("boardcategories", boardCategories);
		modelAndView.addObject("albumcategories",albumCategories);
		modelAndView.setViewName("loginForm");
		return modelAndView;
	}
	
	@PostMapping(path = {"/login/login.do"})
	public ModelAndView login(@RequestParam(name="userId",required=true) String userId, 
			@RequestParam(name="password", required=true) String password,
			RedirectAttributes redirectAttr,
			ModelAndView modelAndView,HttpServletRequest request,
			HttpServletResponse response){
		HttpSession session = request.getSession();
		int isUser= userService.checkUser(userId, password);
		if(isUser == 1){
			UserDto user = userService.getUser(userId, password);
			session.setAttribute("isLogined", true);
			session.setAttribute("user", user);
			String prePage = (String) session.getAttribute("prePage");
			if(prePage != null){
				modelAndView.setViewName("redirect:" + prePage);
				session.removeAttribute("prePage");
			}
			else{
				modelAndView.setViewName("redirect:/main");
			}
		}
		else{
			redirectAttr.addFlashAttribute("errorMessage", "아이디나 비밀번호를 확인하세요");
			redirectAttr.addFlashAttribute("userId",userId);
			redirectAttr.addFlashAttribute("password",password);
			modelAndView.setViewName("redirect:../login");
		}
		return modelAndView;
	}
	@RequestMapping(path = {"/login/logout.do"})
	public ModelAndView logout(
			ModelAndView modelAndView,HttpServletRequest request,
			HttpServletResponse response){
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		session.removeAttribute("isLogined");
		modelAndView.setViewName("redirect:/main");
		return modelAndView;
	}
	@RequestMapping(path = {"/signup"})
	public ModelAndView signUpForm(ModelAndView modelAndView){
		modelAndView.setViewName("signupForm");
		List<CategoryDto> boardCategories = postService.readCategories();
		List<CategoryDto> albumCategories = photoService.readCategories();
		modelAndView.addObject("boardcategories", boardCategories);
		modelAndView.addObject("albumcategories",albumCategories);
		return modelAndView;
	}
	@PostMapping(path = {"/signup/signup.do"})
	public ModelAndView signUp(@ModelAttribute UserDto user,
							ModelAndView modelAndView){
		int result = userService.addUser(user);
		
		if(result != 1){
			modelAndView.setViewName("signupForm");
		}else{
			modelAndView.setViewName("loginForm");
		}
		return modelAndView;
	}
	
	@RequestMapping(path = {"/setting"})
	public ModelAndView setting(ModelAndView modelAndView){
		List<CategoryDto> boardCategories = postService.readCategories();
		List<CategoryDto> albumCategories = photoService.readCategories();
		modelAndView.addObject("boardcategories", boardCategories);
		modelAndView.addObject("albumcategories",albumCategories);
		modelAndView.setViewName("setting");
		return modelAndView;
	}
}

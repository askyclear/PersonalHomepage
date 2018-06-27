package com.hudini.totalhomepage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hudini.totalhomepage.dto.BoardDto;
import com.hudini.totalhomepage.dto.CategoryDto;
import com.hudini.totalhomepage.dto.PhotoDto;
import com.hudini.totalhomepage.dto.UserDto;
import com.hudini.totalhomepage.service.BoardService;

/*
 * 앨범 Controller 요청에 따른 view를 리턴해줌
 * 생성날짜 : 18.06.27
 * 최종수정날짜 : 18.06.27
 * 작성자 : 김대선
 */
@Controller
@RequestMapping(path={"/album"})
public class AlbumController {
	@Autowired
	BoardService<PhotoDto> photoService;
	@Autowired
	BoardService<BoardDto> postService;
	@RequestMapping(path={"/write"})
	public ModelAndView write(@RequestParam(name="albumCategoryId",defaultValue="1")int albumCategoryId,
									ModelAndView modelAndView, HttpServletRequest request){
		HttpSession session = request.getSession();

		if (session.getAttribute("user") != null && session.getAttribute("user") instanceof UserDto) {
			// UserDto user = (UserDto) session.getAttribute("user");
			List<CategoryDto> boardCategories = postService.readCategories();
			List<CategoryDto> albumCategories = photoService.readCategories();
			modelAndView.addObject("boardcategories", boardCategories);
			modelAndView.addObject("albumcategories",albumCategories);
			modelAndView.addObject("curCategory", albumCategoryId);
			modelAndView.setViewName("albumWriteForm");

		} else {
			modelAndView.setViewName("redirect:../login");
			session.setAttribute("prePage", request.getRequestURL() + "?" + request.getQueryString());
		}
		return modelAndView;
	}
}

package com.hudini.totalhomepage.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hudini.totalhomepage.dto.BoardDto;
import com.hudini.totalhomepage.dto.BoardFileDto;
import com.hudini.totalhomepage.dto.CategoryDto;
import com.hudini.totalhomepage.dto.FileInfoDto;
import com.hudini.totalhomepage.dto.UserDto;
import com.hudini.totalhomepage.service.BoardService;
/*
 * 게시판 Controller 요청에 따른 view를 리턴해줌
 * 생성날짜 : 18.06.06
 * 최종수정날짜 : 18.06.15
 * 작성자 : 김대선
 */

@Controller
@RequestMapping(path = "/board")
public class BoardController {
	private String uploadPath = "/boardFile/";
	@Autowired
	BoardService<BoardDto> postService;

	@RequestMapping(path = { "/write" })
	public ModelAndView writeForm(@RequestParam(name = "boardCegoryId", defaultValue = "1") int boardCategoryId,
			ModelAndView modelAndView, HttpServletRequest request) {
		// session이 존재한다면 session을 없다면 null을 반환
		HttpSession session = request.getSession();

		if (session.getAttribute("user") != null && session.getAttribute("user") instanceof UserDto) {
			// UserDto user = (UserDto) session.getAttribute("user");
			List<CategoryDto> categories = postService.readCategories();

			modelAndView.addObject("categories", categories);
			modelAndView.addObject("curCategory", boardCategoryId);
			modelAndView.setViewName("writeForm");

		} else {
			modelAndView.setViewName("redirect:../login");
			session.setAttribute("prePage", request.getRequestURL() + "?" + request.getQueryString());
		}
		return modelAndView;
	}

	//
	@PostMapping(path = { "/write.do" })
	public String write(@RequestParam("fileUpload") MultipartFile file, @ModelAttribute BoardDto board,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null && session.getAttribute("user") instanceof UserDto) {
			UserDto user = (UserDto) session.getAttribute("user");
			board.setUserId(user.getId());
		} else {
			board.setUserId(0);
		}
		int fileId = 0;
		//업로드 파일이 존재시 size > 0보다 클것임
		if (file.getSize() > 0) {
			FileInfoDto fileInfo = new FileInfoDto();
			try {
				String fileName = file.getOriginalFilename();
				String contentType = file.getContentType();
				fileInfo.setFileName(fileName);
				fileInfo.setContentType(contentType);
				fileInfo.setDeleteFlag(0);
				
				InputStream is = file.getInputStream();
				String savedName = uploadFile(fileName, is);
				fileInfo.setSaveFileName(savedName);
			} catch (Exception e) {
				throw new RuntimeException("file save error");
			}
			fileId = postService.addFile(fileInfo);
		}

		int boardId = postService.write(board, fileId);
		if (boardId != 0) {
			return "redirect:../board?boardCategoryId=" + board.getBoardCategoryId();
		} else {
			return "redirect:../board?boardCategoryId=" + board.getBoardCategoryId();
		}

	}

	@RequestMapping(path = { "/read.do" })
	public ModelAndView read(@RequestParam(name = "boardIndex", required = true) int id,
			@RequestParam(name = "boardCegoryId") int boardCegoryId, ModelAndView modelAndView) {
		BoardDto board = postService.read(id);
		List<CategoryDto> categories = postService.readCategories();
		modelAndView.addObject("categories", categories);
		modelAndView.addObject("preCategoryId", boardCegoryId);
		modelAndView.addObject("board", board);
		modelAndView.setViewName("readPost");
		return modelAndView;
	}
	
	private String uploadFile(String originalName, InputStream is) throws IOException {
		UUID uuid = UUID.randomUUID();
		String savedName = uuid.toString() + "_" + originalName;
		File newFile = new File(uploadPath, savedName);

		if (!newFile.exists()) {
			newFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(newFile);
		int readCount = 0;
		byte[] buffer = new byte[1024];
		while((readCount = is.read(buffer)) != -1){
			fos.write(buffer,0,readCount);
			fos.flush();
		}
		fos.close();
		return uploadPath + savedName;
	}
}

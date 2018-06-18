package com.hudini.totalhomepage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hudini.totalhomepage.dto.BoardDto;
import com.hudini.totalhomepage.dto.CategoryDto;
import com.hudini.totalhomepage.dto.FileInfoDto;
import com.hudini.totalhomepage.dto.UserDto;
import com.hudini.totalhomepage.service.BoardService;
/*
 * 게시판 Controller 요청에 따른 view를 리턴해줌
 * 생성날짜 : 18.06.06
 * 최종수정날짜 : 18.06.18
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
		// 업로드 파일이 존재시 size > 0보다 클것임
		if (file.getSize() > 0) {
			FileInfoDto fileInfo = new FileInfoDto();
			try {
				String fileName = file.getOriginalFilename();
				String contentType = file.getContentType();
				fileInfo.setFileName(fileName);
				fileInfo.setContentType(contentType);
				fileInfo.setDeleteFlag(0);
				String root_path = session.getServletContext().getRealPath("/");
				InputStream is = file.getInputStream();
				String savedName = uploadFile(fileName, root_path, is);
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
			@RequestParam(name = "boardCegoryId") int boardCegoryId,
			@RequestParam(name = "isYourBoard",required=false) String isYourBoard, ModelAndView modelAndView) {
		int count = postService.viewCountPlus(id);
		BoardDto board = postService.read(id);
		List<FileInfoDto> boardFiles = postService.readFiles(board.getId());
		List<CategoryDto> categories = postService.readCategories();
		modelAndView.addObject("categories", categories);
		modelAndView.addObject("preCategoryId", boardCegoryId);
		modelAndView.addObject("isYourBoard",isYourBoard);
		modelAndView.addObject("board", board);
		modelAndView.addObject("boardFiles", boardFiles);
		modelAndView.setViewName("readPost");
		return modelAndView;
	}

	@RequestMapping(path = { "/{fileName}/{boardId}" })
	public void download(@PathVariable(name = "fileName") String fileName, @PathVariable(name = "boardId") int boardId,
			HttpServletRequest request, HttpServletResponse response) {
		// boardId와 fileName을 통해 특정 게시판의 fileName인 file정보를 가져옴
		FileInfoDto fileInfo = postService.readFile(boardId, fileName);

		int deleteFlag = fileInfo.getDeleteFlag();
		if (deleteFlag == 0) {
			String saveFileName = fileInfo.getSaveFileName();
			String contentType = fileInfo.getContentType();
			String contextPath = request.getServletContext().getRealPath("/");
			String header = request.getHeader("User-Agent");
			String encodingFileName = "";

			File downloadFile = new File(contextPath, saveFileName);
			long fileLength = 0;

			if (downloadFile.exists()) {
				fileLength = downloadFile.length();
			}

			try {
				encodingFileName = changeEncodingFileName(header, fileName);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Type", contentType);
			response.setHeader("Content-Length", "" + fileLength);
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");
			try {

				FileInputStream fis = new FileInputStream(downloadFile);
				OutputStream fos = response.getOutputStream();

				int readCount = 0;
				byte[] buffer = new byte[1024];
				while ((readCount = fis.read(buffer)) != -1) {
					fos.write(buffer, 0, readCount);

				}
				fos.flush();
				fis.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("file Save Error");
			}
		}
	}

	private String uploadFile(String originalName, String root_path, InputStream is) throws IOException {
		UUID uuid = UUID.randomUUID();
		String savedName = uuid.toString() + "_" + originalName;
		File newFile = new File(root_path + uploadPath, savedName);
		System.out.println(root_path + uploadPath);
		if (!newFile.exists()) {
			newFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(newFile);
		int readCount = 0;
		byte[] buffer = new byte[1024];
		while ((readCount = is.read(buffer)) != -1) {
			fos.write(buffer, 0, readCount);
			fos.flush();
		}
		fos.close();
		return uploadPath + savedName;
	}

	/**
	 * 다운로드 받을때 한글과 특수문자등이 깨지는 현상이 존재한다. 이는 User-Agent Header 에 따라서 한글 및 특수문자
	 * 처리가 다르기 때문 그에 따라 encodedFilename을 그 타입에 맞춰 바꿔준다.
	 * 
	 * @param header
	 *            user-Agent header 정보
	 * @param displayFileName
	 *            원본 파일 이름
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	@PostMapping(path = { "/update" })
	public ModelAndView updateForm(@RequestParam(name = "id", required = true) int boardId, ModelAndView modelAndView,
			HttpServletRequest request, HttpServletResponse response) {
		// session이 존재한다면 session을 없다면 null을 반환
		HttpSession session = request.getSession();
		BoardDto boardDto = postService.read(boardId);
		int curCategoryId = boardDto.getBoardCategoryId();
		if (session.getAttribute("user") != null && session.getAttribute("user") instanceof UserDto) {
			UserDto user = (UserDto) session.getAttribute("user");
			List<CategoryDto> categories = postService.readCategories();
			
			
			//현재 유저와 글을쓴 유저의 id가 같으면
			if(boardDto.getUserId() == user.getId()){
				List<FileInfoDto> fileInfo = postService.readFiles(boardId);
				
				modelAndView.addObject("categories", categories);
				modelAndView.addObject("curCategory", curCategoryId);
				modelAndView.addObject("fileInfo", fileInfo);
				modelAndView.addObject("board",boardDto);
				
				modelAndView.setViewName("writeForm");
			}
			//아니면 아래와 같이
			else { 
				modelAndView.addObject("isYourBoard", "N");
				modelAndView.setViewName("redirect:./read.do?boardIndex=" + boardId + "&boardCegoryId=" + curCategoryId);
			}

		} else {
			modelAndView.setViewName("redirect:../login");
			session.setAttribute("prePage", "/board/read.do?boardIndex=" + boardId + "&boardCegoryId=" + curCategoryId);
		}

		return modelAndView;
	}
	@PostMapping(path = {"/update/update.do"})
	public ModelAndView update(ModelAndView modelAndView,
								@ModelAttribute BoardDto board){
		BoardDto changedDto= postService.modify(board);
		
		int boardId = changedDto.getId();
		int curCategoryId = changedDto.getBoardCategoryId();
		modelAndView.setViewName("redirect:/board/read.do?boardIndex=" + boardId + "&boardCegoryId=" + curCategoryId);
		return modelAndView;
	}
	private String changeEncodingFileName(String header, String displayFileName) throws UnsupportedEncodingException {
		String encodedFilename = "";
		if (header.indexOf("MSIE") > -1) {

			encodedFilename = URLEncoder.encode(displayFileName, "UTF-8").replaceAll("\\+", "%20");

		}

		else if (header.indexOf("Trident") > -1) {

			encodedFilename = URLEncoder.encode(displayFileName, "UTF-8").replaceAll("\\+", "%20");

		}

		else if (header.indexOf("Chrome") > -1) {

			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < displayFileName.length(); i++) {

				char c = displayFileName.charAt(i);

				if (c > '~') {

					sb.append(URLEncoder.encode("" + c, "UTF-8"));

				}

				else {

					sb.append(c);

				}

			}

			encodedFilename = sb.toString();

		}

		else if (header.indexOf("Opera") > -1) {

			encodedFilename = "\"" + new String(displayFileName.getBytes("UTF-8"), "8859_1") + "\"";

		}

		else if (header.indexOf("Safari") > -1) {

			encodedFilename = "\"" + new String(displayFileName.getBytes("UTF-8"), "8859_1") + "\"";

			encodedFilename = URLDecoder.decode(encodedFilename);

		} else {

			encodedFilename = "\"" + new String(displayFileName.getBytes("UTF-8"), "8859_1") + "\"";

			encodedFilename = URLDecoder.decode(encodedFilename);

		}
		return encodedFilename;
	}
}

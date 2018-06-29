package com.hudini.totalhomepage.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hudini.totalhomepage.dto.BoardDto;
import com.hudini.totalhomepage.dto.CategoryDto;
import com.hudini.totalhomepage.dto.FileInfoDto;
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
	private String uploadPath = "/albumFile/";
	@Autowired
	BoardService<PhotoDto> photoService;
	@Autowired
	BoardService<BoardDto> postService;
	@RequestMapping(path={"/write"})
	public ModelAndView writeForm(@RequestParam(name="albumCategoryId",defaultValue="1")int albumCategoryId,
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
	@RequestMapping(path={"write.do"})
	public String write(@RequestParam("fileUpload") MultipartFile file, @ModelAttribute PhotoDto photo,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null && session.getAttribute("user") instanceof UserDto) {
			UserDto user = (UserDto) session.getAttribute("user");
			photo.setUserId(user.getId());
		} else {
			photo.setUserId(0);
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
			fileId = photoService.addFile(fileInfo);
		}

		int albumId = photoService.write(photo, fileId);
		if (albumId != 0) {
			return "redirect:../album?albumCategoryId=" + photo.getAlbumCategoryId();
		} else {
			return "redirect:../album?albumCategoryId=" + photo.getAlbumCategoryId();
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

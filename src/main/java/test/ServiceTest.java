package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hudini.totalhomepage.config.ApplicationContextConfg;
import com.hudini.totalhomepage.dto.BoardDto;
import com.hudini.totalhomepage.dto.PhotoDto;
import com.hudini.totalhomepage.service.BoardService;
import com.hudini.totalhomepage.service.impl.PhotoServiceImpl;
import com.hudini.totalhomepage.service.impl.PostServiceImpl;

public class ServiceTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationContextConfg.class);
//		BoardService<BoardDto> boardService = ac.getBean(PostServiceImpl.class);
//		BoardService<PhotoDto> photoService = ac.getBean(PhotoServiceImpl.class);
//		System.out.println(boardService.readCategories().get(0).getName());
//		System.out.println(photoService.write(new PhotoDto()));
	}

}

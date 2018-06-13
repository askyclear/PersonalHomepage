package test;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hudini.totalhomepage.config.ApplicationContextConfg;
import com.hudini.totalhomepage.dao.BoardDao;
import com.hudini.totalhomepage.dto.BoardDto;

public class DBTest {

	private static ApplicationContext ac;

	public static void main(String[] args) {
		ac = new AnnotationConfigApplicationContext(ApplicationContextConfg.class);
		BoardDto dto = new BoardDto();
		dto.setBoardCategoryId(1);
		dto.setContent("test 중입니다.");
		dto.setTitle("게시판1");
		dto.setUserId(1);
		dto.setCreateDate(new Date());
		dto.setModifyDate(new Date());
		BoardDao dao = ac.getBean(BoardDao.class);
//		for(int i = 0 ; i < 20; i++){
//			dao.insert(dto);
//		}
		for(BoardDto item : dao.selectByBoardCategoryId(0, 0, 5) ){
			System.out.println(item.toString());
		}
		System.out.println(dao.selectById(116));
		
	}

}

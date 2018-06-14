package com.hudini.totalhomepage.dao;

/*
 * board dao 관련 sqls
 * 생성날짜 : 18.06.06
 * 최종수정날짜 : 18.06.06 
 * 작성자 : 김대선
 */
public class BoardSqls {
	public static final String SELECT_BY_PAGE = "SELECT id, board_category_id, user_id, title, content, create_date, modify_date, view_count FROM board ORDER BY modify_date DESC, id DESC LIMIT :start, :limit";
	public static final String SELECT_BY_ID = "SELECT id, board_category_id, user_id, title, content, create_date, modify_date, view_count FROM board WHERE id = :id";
	public static final String SELECT_BY_CATEGORY_ID = "SELECT id, board_category_id, user_id, title, content, create_date, modify_date, view_count FROM board WHERE board_category_id = :boardCategoryId ORDER BY modify_date DESC, id DESC LIMIT :start, :limit";
	
	public static final String SELECT_CATEGORIES = "SELECT id, name FROM board_category";
	public static final String SELECT_COUNT = "SELECT count(*) FROM board ORDER BY modify_date DESC, id DESC";
	public static final String SELECT_COUNT_BY_CATEGORI_ID = "SELECT count(*) FROM board WHERE board_category_id = :boardCategoryId ORDER BY modify_date DESC, id DESC";
}

package com.hudini.totalhomepage.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
/*
 * applicationContext class
 * dao와 service, datasource bean을 불러온다.
 * 생성날짜 : 18.06.06
 * 최종수정날짜 : 18.06.06
 * 작성자 : 김대선
 */
@Configuration
@ComponentScan(basePackages = {"com.hudini.totalhomepage.dao", "com.hudini.totalhomepage.service"})
@Import({DBConfig.class})
public class ApplicationContextConfg {

}

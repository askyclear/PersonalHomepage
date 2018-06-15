package com.hudini.totalhomepage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/*
 * Sping MVC에서의 각종 cofiguraion 설정
 * 작성날짜 : 18.06.06
 * 최종수정날짜 : 18.06.15
 * 작성자 : 김대선
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.hudini.totalhomepage.controller"})
public class WebMvcConfiguration extends WebMvcConfigurerAdapter{
	private int cachePeriod = 31556926;
	
	//리소스들에 대한 요청을 어떻게 지정할지
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/*").addResourceLocations("/WEB-INF/css/").setCachePeriod(cachePeriod);
		registry.addResourceHandler("/img/*").addResourceLocations("/WEB-INF/img/").setCachePeriod(cachePeriod);
		registry.addResourceHandler("/js/*").addResourceLocations("/WEB-INF/js/").setCachePeriod(cachePeriod);
		registry.addResourceHandler("/fonts/*").addResourceLocations("/WEB-INF/fonts/").setCachePeriod(cachePeriod);
		registry.addResourceHandler("/boardFile/*").addResourceLocations("/boardFile/").setCachePeriod(cachePeriod);
	}

	//
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("main");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new LogInterceptor());
//	}

//	@Override
//	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//		argumentResolvers.add(new HeaderMapArgumentResolver());
//	}
	/**
	 * upload 관련 Bean 생성 메소드 
	 * @return
	 */
	@Bean
	public MultipartResolver mutilpartResolver(){
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(1024 * 1024 * 10); //10MB 까지 업로드 가능
		return multipartResolver;
	}
}

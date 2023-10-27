package com.gamventory.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
     
     //application.properties 의 uploadPath값을 일어와서 문자열로 저장
    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //웹브라우저에 입력하는 url에 /images로 시작하면 uploadPath에서 폴더를 기준으로 파일을 읽어옴
        registry.addResourceHandler("/images/**") 
                    .addResourceLocations(uploadPath); 
    }

}


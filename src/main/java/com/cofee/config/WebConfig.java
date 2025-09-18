package com.cofee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration//해당 클래스를 객체로 만들어 주지만 파일은 설정용.
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET","POST","DELETE","OPTIONS","PATCH","PUT")
                .allowCredentials(true);//String... --> 가변 매개 변수 ...
    }

    //application.properties 파일에서 uploadPath 항목의 값을 변수에 할당
    @Value("${uploadPath}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);

    }

}

package com.cofee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
}

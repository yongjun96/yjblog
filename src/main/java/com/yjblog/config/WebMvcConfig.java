package com.yjblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor()) // 처음에 생성했던 인터셉터를 넣음
                .addPathPatterns("/test/**") // 인증이 있어야만 접근 가능
                .excludePathPatterns("/foo/**"); // 인증없이 접근 가능

    }
}

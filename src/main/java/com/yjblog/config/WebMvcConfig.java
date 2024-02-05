package com.yjblog.config;

import com.yjblog.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final SessionRepository sessionRepository;

//    @Override
//    // AuthInterceptor 를 메모리에 올림
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AuthInterceptor()) // 생성했던 인터셉터를 넣음
//                //.addPathPatterns("/foo/**") // 인증이 있어야만 접근 가능
//                .excludePathPatterns("/error", "/favicon.ioc", "/foo/**"); // 인증 없이 접근 가능
//    }


    @Override
    // AuthResolver 를 메모리에 올림
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthResolver(sessionRepository)); // 생성했던 리솔버를 넣음
    }
}

package com.yjblog.config;

import com.yjblog.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Security 를 사용할 것이기 때문에 사용하지 않을 예정
 * todo Spring Security 를 사용할 것이기 때문에 사용하지 않을 예정
 */

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtProvider jwtProvider;

    private final AppConfig appConfig;

//    @Override
//    // AuthInterceptor 를 메모리에 올림
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AuthInterceptor()) // 생성했던 인터셉터를 넣음
//                //.addPathPatterns("/foo/**") // 인증이 있어야만 접근 가능
//                .excludePathPatterns("/error", "/favicon.ioc", "/foo/**"); // 인증 없이 접근 가능
//    }


//    // spring security 를 사용할 것이기 때문에 주석
//    @Override
//    // AuthResolver 를 메모리에 올림
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(new AuthResolver(sessionRepository, jwtProvider, appConfig)); // 생성했던 리솔버를 넣음
//    }
}

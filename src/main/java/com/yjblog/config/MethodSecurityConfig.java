package com.yjblog.config;

import com.yjblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity // prePostEnabled = true (default) : @PreAuthorize("hasRole('ROLE_USER')") 컨트롤러에 직접 명령 가능
@RequiredArgsConstructor
public class MethodSecurityConfig {

    private final PostRepository postRepository;

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(){
        var handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(new YjblogPermissionEvaluator(postRepository));
        return handler;
    }

}

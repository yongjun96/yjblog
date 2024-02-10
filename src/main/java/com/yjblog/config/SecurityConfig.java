package com.yjblog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjblog.config.handler.Http401Handler;
import com.yjblog.config.handler.Http403Handler;
import com.yjblog.config.handler.LoginFailHandler;
import com.yjblog.domain.User;
import com.yjblog.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import java.io.IOException;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

/**
 * 해당 config는 잠시 동안 사용하지 않을 예정.
 */

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                    .requestMatchers("/favicon.ico")
                    .requestMatchers("/error")
                    .requestMatchers(toH2Console());
                    //.requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/security/auth/login").permitAll()
                        .requestMatchers("/security/auth/signup").permitAll()
                        .requestMatchers("/security/auth/user").hasAnyRole("USER", "ADMIN")
                        //.requestMatchers("/security/auth/admin").hasRole("ADMIN")
                        .requestMatchers("/security/auth/admin")
                        .access(new WebExpressionAuthorizationManager("hasRole('ADMIN') AND hasAnyAuthority('WRITE')")) // 관리자 역할도 있고 쓰기 권한도 있는 사람만 접근 가능
                        .anyRequest().authenticated())

                .formLogin((from) -> from
                        .loginPage("/security/auth/login") // 로그인 페이지의 주소
                        .loginProcessingUrl("/security/auth/login") // 값을 받아서 검증하는 주소
                        .usernameParameter("username") // input name
                        .passwordParameter("password")
                        .defaultSuccessUrl("/security/auth/") // 성공했을 때 이동할 주소
                        .failureHandler(new LoginFailHandler(objectMapper))
                )

                .exceptionHandling(e -> {
                    e.accessDeniedHandler(new Http403Handler(objectMapper));
                    e.authenticationEntryPoint(new Http401Handler(objectMapper)); // 401 로그인이 필요한 페이지인데 로그인이 안된 상태로 접근 시 로그인 요청 핸들러
                    // 세션 정보, 쿠키 정보 관련 핸들러 알아보기
                })

                .rememberMe(rm -> rm.rememberMeParameter("remember") // 자동 로그인 하기 (로그인 정보 기억하기)
                        .alwaysRemember(false)
                        .tokenValiditySeconds(2592000)
                )

                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){

        return username -> {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));

            return new UserPrincipal(user);
        };

//        //인 메모리 방식
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(); // 매니저 생성
//
//        //유저 정보 입력
//        UserDetails user = User
//                .withUsername("yongjun")
//                .password("1234")
//                .roles("ADMIN")
//                .build();
//
//        manager.createUser(user); // 매니저에 유저정보 반환
//        return manager;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

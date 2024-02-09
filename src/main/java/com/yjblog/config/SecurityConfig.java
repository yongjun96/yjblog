package com.yjblog.config;

import com.yjblog.domain.User;
import com.yjblog.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

/**
 * 해당 config는 잠시 동안 사용하지 않을 예정.
 */

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return web -> web.ignoring()
//                    .requestMatchers("/favicon.ico")
//                    .requestMatchers("/error")
//                    .requestMatchers(toH2Console());
//                    //.requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        return http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/security/auth/login").permitAll()
//                        .requestMatchers("/security/auth/signup").permitAll()
//                        .requestMatchers("/user").hasRole("USER")
//                        .requestMatchers("/admin").hasRole("ADMIN")
//                        .anyRequest().authenticated())
//
//                .formLogin((from) -> from
//                        .loginPage("/security/auth/login") // 로그인 페이지의 주소
//                        .loginProcessingUrl("/security/auth/login") // 값을 받아서 검증하는 주소
//                        .usernameParameter("username") // input name
//                        .passwordParameter("password")
//                        .defaultSuccessUrl("/")) // 성공했을 때 이동할 주소
//
//                .rememberMe(rm -> rm.rememberMeParameter("remember") // 자동 로그인 하기 (로그인 정보 기억하기)
//                        .alwaysRemember(false)
//                        .tokenValiditySeconds(2592000)
//                )
//
//                .csrf(AbstractHttpConfigurer::disable)
//                .build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(UserRepository userRepository){
//
//        return username -> {
//            User user = userRepository.findByEmail(username)
//                    .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
//
//            return new UserPrincipal(user);
//        };
//
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
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new SCryptPasswordEncoder(16, 8, 1, 32, 64);
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

}

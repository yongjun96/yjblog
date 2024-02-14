package com.yjblog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjblog.config.filter.EmailPasswordAuthFilter;
import com.yjblog.config.handler.Http401Handler;
import com.yjblog.config.handler.Http403Handler;
import com.yjblog.config.handler.LoginFailHandler;
import com.yjblog.config.handler.LoginSuccessHandler;
import com.yjblog.domain.User;
import com.yjblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;


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
    private final UserRepository userRepository;

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
                        //.requestMatchers("/security/auth/login").permitAll()
                        //.requestMatchers("/security/auth/signup").permitAll()
                        //.requestMatchers("/security/auth/user").hasAnyRole("USER", "ADMIN")
                        //.requestMatchers("/security/auth/admin").hasRole("ADMIN")
                        .requestMatchers("/security/auth/admin")
                        .access(new WebExpressionAuthorizationManager("hasRole('ADMIN') AND hasAnyAuthority('WRITE')")) // 관리자 역할도 있고 쓰기 권한도 있는 사람만 접근 가능
                        .anyRequest().permitAll())

                .addFilterBefore(usernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .formLogin((from) -> from
//                        .loginPage("/security/auth/login") // 로그인 페이지의 주소
//                        .loginProcessingUrl("/security/auth/login") // 값을 받아서 검증하는 주소
//                        .usernameParameter("username") // input name
//                        .passwordParameter("password")
//                        .defaultSuccessUrl("/security/auth/") // 성공했을 때 이동할 주소
//                        .failureHandler(new LoginFailHandler(objectMapper))
//                )

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
    public EmailPasswordAuthFilter usernamePasswordAuthenticationFilter(){

        EmailPasswordAuthFilter filter = new EmailPasswordAuthFilter("/security/auth/login", objectMapper);
        filter.setAuthenticationManager(authenticationManager()); // custom 한 메서드 호출
        //filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/security/auth/")); // 로그인 성공 시 url 이동
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler(objectMapper)); // 로그인 성공 시 Json 으로 값을 넘겨 주거나 다른 행동을 할 custom handler 생성
        filter.setAuthenticationFailureHandler(new LoginFailHandler(objectMapper));
        filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository()); // 세션이 발급되기 때문에 꼭 있어야 함.

        // org.springframework.session 으로 사용.
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        rememberMeServices.setAlwaysRemember(true);
        rememberMeServices.setValiditySeconds(3600 * 24 *30); // 유효기간 한달

        filter.setRememberMeServices(rememberMeServices);

        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager(){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService(userRepository));
        provider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(provider);
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

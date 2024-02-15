package com.yjblog.config;

//@Configuration
//@EnableWebSecurity // spring security 에서 관리 됨
public class SpringSecurityConfig {
//
//    // SecurityFilterChain interface 임
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//
//        return http
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/", "/login", "/loginPage", "/join", "/joinPage").permitAll()
//                        .requestMatchers("/admin").hasRole("ADMIN")
//                        .requestMatchers("/my/**").hasAnyRole("ADMIN, USER")
//                        .anyRequest().authenticated()
//                )
//
//                .formLogin((auth) -> auth
//                        .loginPage("/login")
//                        .loginProcessingUrl("/loginPage")
//                        .permitAll())
//
//                .csrf(AbstractHttpConfigurer::disable)
//
//                .build();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
}

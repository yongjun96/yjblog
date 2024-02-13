package com.yjblog.controller;

import com.yjblog.config.AppConfig;
import com.yjblog.config.UserPrincipal;
import com.yjblog.request.Signup;
import com.yjblog.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/security/auth")
public class SpringSecurityAuthController {

    private final AuthService authService;

//    @PostMapping("/login")
//    public String login(){
//        return "로그인 페이지입니다.";
//    }

    @GetMapping("/")
    public String main(){
        return "메인 페이지입니다.";
    }


    // Spring EL : 문법 사용
    // security 의 @EnableMethodSecurity 기능을 사용해서 ROLE 추가
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")
    // @AuthenticationPrincipal 지정해 줘야 함 / password 는 객체에서만 null 로 표시해줌 값이 없는게 아님.
    public String user(@AuthenticationPrincipal UserPrincipal userPrincipal){
        userPrincipal.getUserId();
        return "사용자 페이지입니다.";
    }

    // Spring EL : 문법 사용
    // security 의 @EnableMethodSecurity 기능을 사용해서 ROLE 추가
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String admin(){
        return "관리자 페이지입니다.";
    }

    @PostMapping("/signup")
    public void signup(@RequestBody Signup signup){
        authService.signup(signup);
    }
}

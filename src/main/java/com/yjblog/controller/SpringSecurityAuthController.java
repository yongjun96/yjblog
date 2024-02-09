package com.yjblog.controller;

import com.yjblog.config.AppConfig;
import com.yjblog.request.Signup;
import com.yjblog.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/security/auth")
public class SpringSecurityAuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String login(){
        return "로그인 페이지입니다.";
    }

//    @PostMapping("/signup")
//    public void signup(@RequestBody Signup signup){
//        authService.signup(signup);
//    }
}

package com.yjblog.controller;

import com.yjblog.domain.User;
import com.yjblog.exception.InvalidRequest;
import com.yjblog.exception.InvalidSigningInformation;
import com.yjblog.repository.UserRepository;
import com.yjblog.request.Login;
import com.yjblog.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public void login(@RequestBody @Valid Login login){
        // json 아이디/비밀번호
        log.info(">>> login : {}", login);

        // DB에서 조회
        User user = authService.signing(login);

        // 토큰을 응답

    }
}

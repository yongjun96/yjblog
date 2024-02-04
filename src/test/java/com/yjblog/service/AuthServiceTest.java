package com.yjblog.service;

import com.yjblog.domain.Session;
import com.yjblog.domain.User;
import com.yjblog.repository.UserRepository;
import com.yjblog.request.Login;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("사용자 로그인")
    void login() {

        //given
        Login login = Login.builder()
                .email("yongjun96@gmail.com")
                .password("1234")
                .build();

        //when
        String accessToken = authService.signing(login);

        //then
        assertThat(accessToken).isNotNull();
    }
}
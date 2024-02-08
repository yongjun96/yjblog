package com.yjblog.service;

import com.yjblog.domain.Session;
import com.yjblog.domain.User;
import com.yjblog.exception.AlreadExistsEmailException;
import com.yjblog.repository.UserRepository;
import com.yjblog.request.Login;
import com.yjblog.request.Signup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void clean(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 로그인")
    void login() {

        //given
        User user = User.builder()
                .email("yongjun96@gmail.com")
                .password("1234")
                .name("김용준")
                .build();

        userRepository.save(user);

        Login login = Login.builder()
                .email("yongjun96@gmail.com")
                .password("1234")
                .build();

        //when
        String accessToken = authService.signing(login);

        //then
        assertThat(accessToken).isNotNull();
    }

    @Test
    @DisplayName("회원가입 성공")
    void signup(){

        //given
        Signup signup = Signup.builder()
                .email("yongjun96@gmail.com")
                .name("kimYongJun")
                .password("1234")
                .build();

        //when
        User user = authService.signup(signup);

        //then
        Assertions.assertThat(1).isEqualTo(userRepository.count());
        Assertions.assertThat(user.getEmail()).isEqualTo(signup.getEmail());
        Assertions.assertThat(user.getName()).isEqualTo(signup.getName());
        Assertions.assertThat(user.getPassword()).isEqualTo(signup.getPassword());
    }


    @Test
    @DisplayName("회원가입 실패")
    void signupFail(){

        //given
        User userBuilder = User.builder()
                .email("yongjun96@gmail.com")
                .name("kimYongJun")
                .password("1234")
                .build();

        userRepository.save(userBuilder);

        Signup signup = Signup.builder()
                .email("yongjun96@gmail.com")
                .name("kimYongJun")
                .password("1234")
                .build();

        //when
        assertThrows(AlreadExistsEmailException.class, () -> authService.signup(signup));

    }
}
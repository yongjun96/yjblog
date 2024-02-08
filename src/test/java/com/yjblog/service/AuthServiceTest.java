package com.yjblog.service;

import com.yjblog.crypto.PasswordEncoder;
import com.yjblog.domain.User;
import com.yjblog.exception.AlreadExistsEmailException;
import com.yjblog.exception.InvalidSigningInformation;
import com.yjblog.repository.UserRepository;
import com.yjblog.request.Login;
import com.yjblog.request.Signup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AuthServiceTest {

    @Autowired private UserRepository userRepository;
    @Autowired private AuthService authService;
    @Autowired private PasswordEncoder passwordEncoder;

    @BeforeEach
    void clean(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 로그인")
    void login() {

        //given
        String encodePassword = passwordEncoder.passwordEncoder("1234");

        User user = User.builder()
                .email("yongjun96@gmail.com")
                .name("kimYongJun")
                .password(encodePassword)
                .build();

        userRepository.save(user);

        Login login = Login.builder()
                .email("yongjun96@gmail.com")
                .password("1234")
                .build();

        //when
        Long userId = authService.jwtSigning(login);

        //then
        assertThat(userId).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("사용자 로그인 (비밀번호 틀림)")
    void loginPasswordFail() {

        //given
        Signup signup = Signup.builder()
                .email("yongjun96@gmail.com")
                .name("kimYongJun")
                .password("1234")
                .build();

        User user = authService.signup(signup);

        Login login = Login.builder()
                .email(signup.getEmail())
                .password("12345")
                .build();

        //when
        assertThrows(InvalidSigningInformation.class, () -> authService.jwtSigning(login));
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
        boolean matches = passwordEncoder.matches("1234", user.getPassword());

        //then
        assertThat(1).isEqualTo(userRepository.count());
        assertThat(user.getEmail()).isEqualTo(signup.getEmail());
        assertThat(user.getName()).isEqualTo(signup.getName());
        assertThat(matches).isTrue();

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
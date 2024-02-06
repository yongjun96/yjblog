package com.yjblog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjblog.domain.Session;
import com.yjblog.domain.User;
import com.yjblog.exception.InvalidSigningInformation;
import com.yjblog.exception.Unauthorized;
import com.yjblog.repository.SessionRepository;
import com.yjblog.repository.UserRepository;
import com.yjblog.request.Login;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    void clean(){
        //클린 수행
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void findByIdTest() throws Exception {

        Login login = Login.builder()
                .email("yongjun96@gmail.com")
                .password("1234")
                .build();

        User user = User.builder()
                .name("yongjun")
                .email("yongjun96@gmail.com")
                .password("1234")
                .build();

        userRepository.save(user);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @Transactional
    @DisplayName("로그인 성공 후 세션 1개 생성")
    void findByIdSessionTest() throws Exception {

        Login login = Login.builder()
                .email("yongjun96@gmail.com")
                .password("1234")
                .build();

        User user = User.builder()
                .name("yongjun")
                .email("yongjun96@gmail.com")
                .password("1234")
                .build();

        userRepository.save(user);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(user.getSessions().size()).isEqualTo(1L);

    }


    @Test
    @DisplayName("로그인 성공 후 session 응답")
    void findByIdSessionResponseTest() throws Exception {

        Login login = Login.builder()
                .email("yongjun96@gmail.com")
                .password("1234")
                .build();

        User user = User.builder()
                .name("yongjun")
                .email("yongjun96@gmail.com")
                .password("1234")
                .build();

        userRepository.save(user);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


// DB의 저장된 세션값을 이용해서 권한을 인증받는 테스트. 지금은 쿠키로 사용.
//    @Test
//    @DisplayName("로그인 후 권한이 필요한 페이지 접속")
//    void authLogin() throws Exception {
//
//        User user = User.builder()
//                .name("yongjun")
//                .email("yongjun96@gmail.com")
//                .password("1234")
//                .build();
//
//        Session session = user.addSession();
//
//        userRepository.save(user);
//
//        mockMvc.perform(RestDocumentationRequestBuilders.get("/foo")
//                        .header("authorization", session.getAccessToken())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }


// DB의 저장된 세션값을 이용해서 권한을 인증받는 테스트. 지금은 쿠키로 사용.
//    @Test
//    @DisplayName("로그인 후 검증되지 않은 세션값으로 권한이 필요한 페이지에 접속할 수 없다.")
//    void authLoginFail() throws Exception {
//
//        User user = User.builder()
//                .name("yongjun")
//                .email("yongjun96@gmail.com")
//                .password("1234")
//                .build();
//
//        Session session = user.addSession();
//
//        userRepository.save(user);
//
//        mockMvc.perform(RestDocumentationRequestBuilders.get("/foo")
//                        .header("authorization", session.getAccessToken() + "-other")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
//    }


    @Test
    @DisplayName("/auth/cookieLogin, 로그인 성공 후, 쿠키 발급")
    void authCookieLogin() throws Exception {

        User user = User.builder()
                .name("yongjun")
                .email("yongjun96@gmail.com")
                .password("1234")
                .build();

        userRepository.save(user);

        Login login = Login.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/cookieLogin")
                        .content(objectMapper.writeValueAsString(login))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.cookie().value("SESSION", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.cookie().domain("SESSION", "localhost"))
                .andExpect(MockMvcResultMatchers.cookie().path("SESSION", "/"))
                .andExpect(MockMvcResultMatchers.cookie().maxAge("SESSION", 2592000))
                .andExpect(MockMvcResultMatchers.cookie().httpOnly("SESSION", true))
                .andExpect(MockMvcResultMatchers.cookie().sameSite("SESSION", "strict"))
                .andExpect(MockMvcResultMatchers.cookie().secure("SESSION", false))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @DisplayName("/auth/cookieLogin, 로그인 실패")
    void authCookieLoginFail() throws Exception {

        User user = User.builder()
                .name("yongjun")
                .email("yongjun96@gmail.com")
                .password("1234")
                .build();

        userRepository.save(user);

        Login login = Login.builder()
                .email(user.getEmail())
                .password("12345")
                .build();

        mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/cookieLogin")
                        .content(objectMapper.writeValueAsString(login))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
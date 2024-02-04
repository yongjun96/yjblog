package com.yjblog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjblog.domain.User;
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
    @Transactional
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
}
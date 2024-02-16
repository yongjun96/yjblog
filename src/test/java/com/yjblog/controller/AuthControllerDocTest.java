package com.yjblog.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjblog.config.YjblogMockUser;
import com.yjblog.repository.PostRepository;
import com.yjblog.repository.UserRepository;
import com.yjblog.request.Login;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
//@WebMvcTest(AuthController.class)
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs
class AuthControllerDocTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void clean(){
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 테스트")
    @YjblogMockUser
    void findByIdTest() throws Exception {

        Login login = Login.builder()
                .email("yongjun96@gmail.com")
                .password("1234")
                .build();

        mockMvc.perform(RestDocumentationRequestBuilders.post("/security/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("test-post",
                        ResourceSnippetParameters.builder()
                                .tag("로그인")
                                .summary("로그인 테스트")
                                .description("로그인 테스트 입니다.")
                                .requestSchema(Schema.schema("MainRequest.Post"))
                                .responseSchema(Schema.schema("MainResponse.Post")),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("yongjun96@gmail.com"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("1234")
                        )));
    }
}


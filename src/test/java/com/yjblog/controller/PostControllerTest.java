package com.yjblog.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.print.attribute.standard.Media;


@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/api/hello 요청시 hello world를 출력한다.")
    void test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/hello")) // 해당 uri -> application/json
                .andExpect(MockMvcResultMatchers.status().isOk()) // 예상되는 http 상태
                .andExpect(MockMvcResultMatchers.content().string("hello world")) //결과값
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts 요청시 Hello World 출력")
    void postsTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목\", \"content\": \"내용\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Post Hello"))
                .andDo(MockMvcResultHandlers.print());
    }
}
package com.yjblog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjblog.domain.Post;
import com.yjblog.repository.PostRepository;
import com.yjblog.request.PostCreate;
import com.yjblog.response.PostEdit;
import com.yjblog.response.PostResponse;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void clean(){
        //클린 수행
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성 /posts 요청")
    void postsTest() throws Exception {

        //given
        PostCreate request = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        String json = objectMapper.writeValueAsString(request);

        System.out.println("json확인 : "+json);

        //expected
        mockMvc
                .perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("글 작성 /posts 요청시 title값은 필수다.")
    void titleNullPostsTest() throws Exception {

        //given
        PostCreate request = PostCreate.builder()
                .title(null)
                .content("내용")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.title").value("제목이 없습니다."))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("/posts 요청시 PostEntity 저장")
    void postCreate() throws Exception {

        //given
        PostCreate request = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc
                .perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        //then
        Assertions.assertThat(postRepository.count()).isEqualTo(1);

        Post post = postRepository.findAll().get(0);
        Assertions.assertThat(post.getTitle()).isEqualTo("제목");
        Assertions.assertThat(post.getContent()).isEqualTo("내용");
    }


    @Test
    @DisplayName("/posts/{postId} 글 1개 조회")
    void postFindById() throws Exception {
        //given
        Post post = Post.builder()
                .title("제목임 10글자 넘길거임~~~!!!!!!")
                .content("내용임~")
                .build();

        // expected -> when + then
        postRepository.save(post);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(post.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("제목임 10글자 넘길거임~~~!!!!!!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("내용임~"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("/posts 글 여러개 조회")
    void postFindAll() throws Exception {
        //given
        List<Post> requestPosts = IntStream.range(0, 30)
                .mapToObj(i -> Post.builder()
                        .title("제목"+i)
                        .content("내용"+i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        // expected -> when + then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/posts?page=0&size=5")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("제목29"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("내용29"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts/{postId} 글 수정")
    void postEdit() throws Exception {

        Post post = Post.builder()
                .title("제목임")
                .content("내용임")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("수정된 제목임")
                .content("수정된 내용임")
                .build();

        String postEditJson = objectMapper.writeValueAsString(postEdit);

        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postEditJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("글 삭제")
    void postDelete() throws Exception {

        Post post = Post.builder()
                .title("제목임")
                .content("내용임")
                .build();

        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}", post.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    void PostFindByIdFail() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 수정")
    void PostEditFail() throws Exception {

        PostEdit postEdit = PostEdit.builder()
                .title("수정된 제목임")
                .content("수정된 내용임")
                .build();

        String postEditJson = objectMapper.writeValueAsString(postEdit);

        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postEditJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("게시글 작성시 제목에 바보는 포함될 수 없다.")
    void PostCreateContains() throws Exception {

        PostCreate postCreate = PostCreate.builder()
                .title("바보입니다.")
                .content("수정된 내용임")
                .build();

        String postEditJson = objectMapper.writeValueAsString(postCreate);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postEditJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
}
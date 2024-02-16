package com.yjblog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjblog.config.YjblogMockUser;
import com.yjblog.domain.Post;
import com.yjblog.domain.User;
import com.yjblog.exception.UserNotFound;
import com.yjblog.repository.PostRepository;
import com.yjblog.repository.UserRepository;
import com.yjblog.request.PostCreate;
import com.yjblog.response.PostEdit;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void clean(){
        //클린 수행
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    // security 의 인증을 만들어서 테스트
    // 항목들을 custom 할 수 있도록 기능을 제공 어노테이션 직접 만들어야 함
    @YjblogMockUser // config -> YjblogMockSecurityContext 에서 디폴트 값을 세팅 하였기 때문에 검증 통과
    //@WithMockUser(username = "yongjun96@gmail.com", roles = {"ADMIN"}) // @AuthenticationPrincipal 를 인식하지 못함
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
                .andDo(MockMvcResultHandlers.print());

        assertThat(postRepository.count()).isEqualTo(1);

        Post post = postRepository.findAll().get(0);

        assertThat(request.getTitle()).isEqualTo(post.getTitle());
        assertThat(request.getContent()).isEqualTo(post.getContent());
    }

    @Test
    @DisplayName("글 작성 /posts 요청시 title값은 필수다.")
    @YjblogMockUser
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
    @YjblogMockUser
    //@WithMockUser(username = "yongjun96@gmail.com", roles = {"ADMIN"})
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
    @YjblogMockUser
    void postEdit() throws Exception {

        //given
        User user = userRepository.findByEmail("yongjun96@gmail.com")
                .orElseThrow(UserNotFound::new);

        Post post = Post.builder()
                .title("제목임")
                .content("내용임")
                .user(user)
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
    @YjblogMockUser
    //@WithMockUser(username = "yongjun96@gmail.com", roles = {"ADMIN"})
    void postDelete() throws Exception {

        //given
        User user = userRepository.findByEmail("yongjun96@gmail.com")
                .orElseThrow(UserNotFound::new);

        Post post = Post.builder()
                .title("제목임")
                .content("내용임")
                .user(user)
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
    @YjblogMockUser
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
    @YjblogMockUser
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
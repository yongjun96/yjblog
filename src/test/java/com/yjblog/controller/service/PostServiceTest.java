package com.yjblog.controller.service;

import com.yjblog.domain.Post;
import com.yjblog.repository.PostRepository;
import com.yjblog.request.PostCreate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void createPost(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        //when
       postService.write(postCreate);

        //then
        Assertions.assertThat(postRepository.count()).isEqualTo(1);
        Post post = postRepository.findAll().get(0);
        Assertions.assertThat(post.getTitle()).isEqualTo("제목");
        Assertions.assertThat(post.getContent()).isEqualTo("내용");
    }

    @Test
    @DisplayName("글 한개 조회")
    void postFindById(){

        // given
        Post requestPost = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(requestPost);

        // when
        Post findPost = postService.get(requestPost.getId());

        // then
        Assertions.assertThat(findPost).isNotNull();
        Assertions.assertThat(postRepository.count()).isEqualTo(1);
        Assertions.assertThat(findPost.getTitle()).isEqualTo("제목");
        Assertions.assertThat(findPost.getContent()).isEqualTo("내용");

    }

}
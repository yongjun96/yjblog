package com.yjblog.service;

import com.yjblog.domain.Post;
import com.yjblog.exception.PostNotFound;
import com.yjblog.repository.PostRepository;
import com.yjblog.request.PostCreate;
import com.yjblog.request.PostSearch;
import com.yjblog.response.PostEdit;
import com.yjblog.response.PostResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

//    @Test
//    @DisplayName("글 작성")
//    void createPost(){
//        //given
//        PostCreate postCreate = PostCreate.builder()
//                .title("제목")
//                .content("내용")
//                .build();
//
//        //when
//       postService.write(postCreate);
//
//        //then
//        Assertions.assertThat(postRepository.count()).isEqualTo(1);
//        Post post = postRepository.findAll().get(0);
//        Assertions.assertThat(post.getTitle()).isEqualTo("제목");
//        Assertions.assertThat(post.getContent()).isEqualTo("내용");
//    }
//
//    @Test
//    @DisplayName("글 한개 조회")
//    void postFindById(){
//
//        // given
//        Post requestPost = Post.builder()
//                .title("제목")
//                .content("내용")
//                .build();
//
//        postRepository.save(requestPost);
//
//        // when
//        PostResponse findPost = postService.get(requestPost.getId());
//
//        // then
//        Assertions.assertThat(findPost).isNotNull();
//        Assertions.assertThat(postRepository.count()).isEqualTo(1);
//        Assertions.assertThat(findPost.getTitle()).isEqualTo("제목");
//        Assertions.assertThat(findPost.getContent()).isEqualTo("내용");
//
//    }
//
//
//    @Test
//    @DisplayName("글 첫 1페이지 조회")
//    void postFindAll(){
//
//        // given
//        List<Post> requestPosts = IntStream.range(0, 30)
//                        .mapToObj(i -> Post.builder()
//                                .title("제목"+i)
//                                .content("내용"+i)
//                                .build())
//                        .collect(Collectors.toList());
//
//
//        postRepository.saveAll(requestPosts);
//
//        PostSearch postSearch = PostSearch.builder()
//                .page(1)
//                .build();
//
//        // when
//        List<PostResponse> posts = postService.getList(postSearch);
//
//        // then
//        Assertions.assertThat(posts.size()).isEqualTo(10);
//        Assertions.assertThat("제목29").isEqualTo(posts.get(0).getTitle());
//        Assertions.assertThat("제목25").isEqualTo(posts.get(4).getTitle());
//    }
//
//    @Test
//    @DisplayName("글 수정")
//    void postModify(){
//
//        //given
//        Post post = Post.builder()
//                .title("제목입니다.")
//                .content("내용입니다.")
//                .build();
//
//        postRepository.save(post);
//
//        PostEdit postEdit = PostEdit.builder()
//                .title("수정된 제목입니다.")
//                .build();
//
//        //when
//        postService.edit(post.getId(), postEdit);
//
//        //then
//        Post findPost = postRepository.findById(post.getId())
//                .orElseThrow(()-> new RuntimeException("해당아이디에 글이 없습니다."));
//
//        Assertions.assertThat(post.getId()).isEqualTo(findPost.getId());
//        Assertions.assertThat(findPost.getTitle()).isEqualTo(postEdit.getTitle());
//        Assertions.assertThat(findPost.getContent()).isEqualTo(findPost.getContent());
//    }
//
//    @Test
//    @DisplayName("글 삭제")
//    void postDelete(){
//        Post post = Post.builder()
//                .title("제목")
//                .content("내용")
//                .build();
//
//        postRepository.save(post);
//
//        postService.delete(post.getId());
//
//        Assertions.assertThat(postRepository.count()).isEqualTo(0);
//
//    }
//
//    @Test
//    @DisplayName("글 수정 (실패 케이스 : 존재하지 않는 글입니다.)")
//    void postModifyFail(){
//
//        //given
//        Post post = Post.builder()
//                .title("제목입니다.")
//                .content("내용입니다.")
//                .build();
//
//        postRepository.save(post);
//
//        PostEdit postEdit = PostEdit.builder()
//                .title("수정된 제목입니다.")
//                .build();
//
//        // expected
//        Assertions.assertThatThrownBy(() -> postService.edit(post.getId() + 1L, postEdit))
//                .isInstanceOf(PostNotFound.class)
//                .hasMessageContaining("존재하지 않는 글입니다.");
//    }
//
//    @Test
//    @DisplayName("글 삭제 (실패 케이스 : 존재하지 않는 글입니다.)")
//    void postDeleteFail(){
//        Post post = Post.builder()
//                .title("제목")
//                .content("내용")
//                .build();
//
//        postRepository.save(post);
//
//        // expected
//        Assertions.assertThatThrownBy(() -> postService.delete(post.getId() + 1L))
//                .isInstanceOf(PostNotFound.class)
//                .hasMessageContaining("존재하지 않는 글입니다.");
//
//    }
//
//
//    @Test
//    @DisplayName("글 한개 조회 (실패 케이스 : 존재하지 않는 글입니다.)")
//    void postFindByIdFail(){
//
//        // given
//        Post post = Post.builder()
//                .title("제목")
//                .content("내용")
//                .build();
//
//        postRepository.save(post);
//
//        // expected
//        Assertions.assertThatThrownBy(() -> postService.get(post.getId() + 1L))
//                .isInstanceOf(PostNotFound.class)
//                .hasMessageContaining("존재하지 않는 글입니다.");
//    }

}
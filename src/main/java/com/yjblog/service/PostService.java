package com.yjblog.service;

import com.yjblog.domain.Post;
import com.yjblog.repository.PostRepository;
import com.yjblog.request.PostCreate;
import com.yjblog.request.PostSearch;
import com.yjblog.response.PostEdit;
import com.yjblog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //글 생성
    public void write(PostCreate postCreate){
        //postCreate -> Entity
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

       postRepository.save(post);
    }

    //글 하나 조회
    public PostResponse get(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        return postResponse;
    }


    //전체 글을 페이징으로 조회
    //글이 너무 많으면 비용이 필요 이상으로 많이 든다.
    public List<PostResponse> getList(PostSearch postSearch) {
         return postRepository.getList(postSearch).stream()
               .map(post -> new PostResponse(post))
                 .collect(Collectors.toList());
    }

    //글 수정
    //@Transactional을 달아 주면 postRepository.save()를 따로 하지 않아도 자동 업데이트 해준다.
    @Transactional
    public void edit(Long id, PostEdit postEdit){
        Post findPost = postRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        findPost.edit(postEdit);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 글입니다."));

        postRepository.delete(post);
    }
}

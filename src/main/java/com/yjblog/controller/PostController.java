package com.yjblog.controller;

import com.yjblog.domain.Post;
import com.yjblog.exception.InvalidRequest;
import com.yjblog.request.PostSearch;
import com.yjblog.response.PostEdit;
import com.yjblog.service.PostService;
import com.yjblog.request.PostCreate;
import com.yjblog.response.PostResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController // 데이터 기반 API응답 생성
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/foo")
    public String foo(){
        return "foo";
    }

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        request.validate();
        postService.write(request);
    }

    /**
     * /posts/{postId} -> 글 한개만 조회
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") @Valid Long postId){
        PostResponse post = postService.get(postId);
        return post;
    }

    /**
     * /posts -> 글 전체 조회 (검색 + 페이징)
     */
    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch){
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void postEdit(@RequestBody @Valid PostEdit postEdit, @PathVariable(name = "postId") Long postId){
        postService.edit(postId, postEdit);
    }

    @DeleteMapping("/posts/{postId}")
    public void postDelete(@PathVariable(name = "postId") Long postId){
        postService.delete(postId);
    }
}

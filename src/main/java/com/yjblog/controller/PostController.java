package com.yjblog.controller;

import com.yjblog.domain.Post;
import com.yjblog.request.PostSearch;
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

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        // Case 1. 저장한 데이터 Entity -> response로 응답하기
        // Case 2. 저장한 데이터의 primary_id -> response로 응답하기
        //         - Client에서는 수신한 id를 post 조회 API를 통해서 글 데이터를 수신받음
        // Case 3. 응답 필요 없음
        //         - Client에서 모든 POST(글) 데이터 context를 관리함.
        // Bad Case : 서버에서 반드시 이렇게 할겁니다! fix -> 매우 안좋음
        postService.write(request);
    }

    /**
     * /posts/{postId} -> 글 한개만 조회
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") @Valid Long id){
        PostResponse post = postService.get(id);
        return post;
    }

    /**
     * /posts -> 글 전체 조회 (검색 + 페이징)
     */
    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch){
        return postService.getList(postSearch);
    }
}

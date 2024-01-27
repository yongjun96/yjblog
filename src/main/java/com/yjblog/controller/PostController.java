package com.yjblog.controller;

import com.yjblog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController // 데이터 기반 API응답 생성
public class PostController {

    @GetMapping("/api/hello")
    public String helloTest(){
        return "hello world";
    }

    @PostMapping("/posts")
    public String post(@RequestBody PostCreate postCreate){
        log.info("title={}, content={}",postCreate.getTitle(), postCreate.getContent());
        return "Post Hello";
    }
}

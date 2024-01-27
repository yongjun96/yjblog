package com.yjblog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 데이터 기반 API응답 생성
public class PostController {


    @GetMapping("/api/hello")
    public String get(){
        return "hello world";
    }

}

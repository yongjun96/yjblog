package com.yjblog.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class dockerTestController {

    @GetMapping("/")
    public String dockerTest(){
        return "HI!! docker test~~";
    }

    @GetMapping("/pageMove")
    public String dockerTestPage(){
        return "페이지 전환이 자유롭게 되나 테스트";
    }

}

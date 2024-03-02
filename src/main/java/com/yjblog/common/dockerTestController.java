package com.yjblog.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class dockerTestController {

    @GetMapping("/")
    public String dockerTest(){
        return "HI!! docker test~~";
    }

}

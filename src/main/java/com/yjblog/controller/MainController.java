package com.yjblog.controller;

import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(){
        return "main";
    }
}

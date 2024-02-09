package com.yjblog.controller;

import com.yjblog.request.Signup;
import com.yjblog.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @GetMapping("/join")
    public String join(){
        return "join";
    }

    @PostMapping("/joinPage")
    public String joinPage(@ModelAttribute Signup signup){

        System.out.println("signup : "+signup.toString());

        joinService.joinProcess(signup);

        return "redirect:/login";
    }
}

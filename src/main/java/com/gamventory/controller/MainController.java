package com.gamventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
  
    @GetMapping(value = "/")
    public String main(){
        return "/main";
    }

    @GetMapping(value = "/members/login")
    public String login(){
        return "/member/login";
    }
}

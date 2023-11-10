package com.gamventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagementController {

    @GetMapping(value = "/managementGameDetail")
    public String managementGameDetail(){
        return "managementGameDetail";
    }


}

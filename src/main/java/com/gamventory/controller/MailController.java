package com.gamventory.controller;

import com.gamventory.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @ResponseBody
    @PostMapping("/mail")
    public String mailSend(String mail) {

        int number = mailService.sendMail(mail);

        String num = "" + number;

        return num;
    }

}

package com.gamventory.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gamventory.dto.MemberFormDto;
import com.gamventory.entity.Member;
import com.gamventory.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/members")
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model) {

        MemberFormDto memberFormDto = MemberFormDto.builder().build();
        model.addAttribute("memberFormDto", memberFormDto);

        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/MemberForm";
        }

        return "redirect:/";
    }

}

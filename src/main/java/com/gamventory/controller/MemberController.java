package com.gamventory.controller;

import java.security.Principal;

import com.gamventory.dto.MemberUpdateFormDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gamventory.dto.MemberFIndDto;
import com.gamventory.dto.MemberFormDto;
import com.gamventory.entity.Member;
import com.gamventory.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/members")
@RequiredArgsConstructor
@Log4j2
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

        log.info("/members/new controller");
        if(bindingResult.hasErrors()) {
            log.info("binding....");
            log.info(bindingResult.toString());
            return "member/memberForm";
        }

        try {
            log.info("member.createMember");
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/MemberForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/info")
    public String memberInfo(Principal principal, Model model) {


        return "/member/memberInfo";
    }

    @GetMapping(value = "/update")
    public String memberUpdate(Model model, Principal principal) {

        MemberUpdateFormDto memberUpdateFormDto  = memberService.getMember(principal.getName());
        log.info(memberUpdateFormDto.toString());
        model.addAttribute("memberUpdateFormDto", memberUpdateFormDto);
        return "/member/memberUpdate";
    }

    @PostMapping(value = "/update")
    public String memberInfoUpdate(@Valid MemberUpdateFormDto memberUpdateFormDto, BindingResult bindingResult,
                                   Principal principal, Model model) {

        log.info(bindingResult.toString());

        log.info("인증");
        if(! memberUpdateFormDto.getEmail().equals(principal.getName())) {

            model.addAttribute("errorMessage", "수정할 수 있는 권한이 없습니다.");
            return "/member/memberUpdate";
        }

        log.info("바인딩");
        log.info(bindingResult.hasErrors());
        if(bindingResult.hasErrors()) {
            return "/member/memberUpdate";
        }

        try {
            log.info("서비스");
            memberService.modifyMember(memberUpdateFormDto);
        } catch(Exception e) {
            model.addAttribute("errorMessage", "회원 정보 수정 중 에러가 발생하였습니다.");
            return "/member/memberUpdate";
        }

        return "/main";
    }

    @GetMapping(value = "/find")
    public String memberFind(Model model) {

        model.addAttribute("memberFindDto", MemberFIndDto.builder().build());
        return "/member/memberFind";
    }

    @PostMapping(value = "/certification_member")
    public String memberFound(@Valid MemberFIndDto memberFindDto, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "/member/find";
        }



        return "/member/certificationMember";
    }

    @GetMapping(value = "/passUpdate")
    public String memberPasswordUpdate() {
        return "/member/memberPasswordUpdate";
    }

    @GetMapping(value = "/login")
    public String loginMember() {
        return "/member/memberLogin";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {

        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해 주세요.");
        return "member/memberLogin";
    }

}

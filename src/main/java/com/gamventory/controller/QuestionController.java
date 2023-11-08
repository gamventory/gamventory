package com.gamventory.controller;

import com.gamventory.entity.Member;
import com.gamventory.entity.Question;
import com.gamventory.service.MemberService;
import com.gamventory.service.QuestionService;
import com.gamventory.validation.AnswerForm;
import com.gamventory.validation.QuestionForm;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping(value = "/customer/question")
@RequiredArgsConstructor
@Log4j2
public class QuestionController {

    private final QuestionService questionService;

    private final MemberService memberService;

    // 리스트
    @GetMapping(value = "/list")
    public String questionListView(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {

        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);

        return "customer/questionList";
    }

    // 상세 페이지
    @GetMapping(value = "/read/{id}")
    public String questionDetailView(@PathVariable("id") Long id, AnswerForm answerForm, Model model) {

        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);

        return "customer/questionDetail";
    }

    // 글쓰기
    @GetMapping(value = "/write")
    public String questionFormView(QuestionForm questionForm) {
        return "customer/questionForm";
    }

    // 글쓰기 검증 및 저장
    @PostMapping(value = "/write")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "customer/questionForm";
        }

        Member member = this.memberService.getMember(principal.getName());

        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), member);

        return "redirect:/customer/question/list";
    }

    // 수정 페이지
    @GetMapping(value = "/update/{id}")
    public String questionUpdateView(QuestionForm questionForm, Model model, @PathVariable("id") Long id, Principal principal) {

        Question question = this.questionService.getQuestion(id);

        if (!question.getMember().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        log.info("question : " + question.toString());

        questionForm.setContent(question.getContent());
        questionForm.setSubject(question.getSubject());

        log.info("questionForm" + questionForm.toString());

        model.addAttribute("questionForm", questionForm);

        return "customer/questionUpdate";
    }

    @PostMapping(value = "/update/{id}")
    public String questionUpdate(@Valid QuestionForm questionForm, BindingResult bindingResult, @PathVariable("id") Long id, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "customer/questionUpdate";
        }

        Question question = this.questionService.getQuestion(id);

        if (!question.getMember().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());

        return String.format("redirect:/customer/question/read/%s", id);
    }

    @GetMapping(value = "/delete/{id}")
    public String questionDelete(@PathVariable("id") Long id, Principal principal) {

        Question question = this.questionService.getQuestion(id);

        if (!question.getMember().getEmail().equals(principal.getName())) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

        this.questionService.delete(question);

        return "redirect:/customer/question/list";
    }

}

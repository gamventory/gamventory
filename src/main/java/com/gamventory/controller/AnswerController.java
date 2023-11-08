package com.gamventory.controller;

import com.gamventory.entity.Answer;
import com.gamventory.entity.Member;
import com.gamventory.entity.Question;
import com.gamventory.service.AnswerService;
import com.gamventory.service.MemberService;
import com.gamventory.service.QuestionService;
import com.gamventory.validation.AnswerForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping(value = "/customer/question/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final QuestionService questionService;

    private final AnswerService answerService;

    private final MemberService memberService;

    // 댓글 저장
    @PostMapping(value = "/create/{id}")
    public String createAnswer(@PathVariable("id") Long id, Model model, @Valid AnswerForm answerForm,
                               BindingResult bindingResult, Principal principal) {

        Question question = this.questionService.getQuestion(id);
        Member member= this.memberService.getMember(principal.getName());

        if(bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "customer/questionDetail";
        }

        Answer answer = this.answerService.create(question, answerForm.getContent(), member);
        return String.format("redirect:/customer/question/read/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
    }

    // 댓글 변경 페이지
    @GetMapping(value = "/modify/{id}")
    public String modifyAnswerView(@PathVariable("id") Long id,  AnswerForm answerForm, Principal principal) {

        Answer answer = this.answerService.getAnswer(id);

        if (!answer.getMember().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정할 권한이 없습니다.");
        }

         answerForm.setContent(answer.getContent());

        return "customer/questionAnswerForm";
    }

    // 댓글 변경
    @PostMapping(value = "/modify/{id}")
    public String modifyAnswer(@Valid AnswerForm answerForm, BindingResult bindingResult, @PathVariable("id") Long id,
                               Principal principal) {

        if (bindingResult.hasErrors()) {
            return "customer/questionAnswerForm";
        }

        Answer answer = this.answerService.getAnswer(id);

        if(!answer.getMember().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        this.answerService.modify(answer, answerForm.getContent());

        return String.format("redirect:/customer/question/read/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
    }

    // 댓글 삭제
    @GetMapping(value = "/delete/{id}")
    public String deleteAnswer(@PathVariable("id") Long id, Principal principal) {

        Answer answer = this.answerService.getAnswer(id);

        if(!answer.getMember().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        this.answerService.delete(answer);

        return String.format("redirect:/customer/question/read/%s", answer.getQuestion().getId());
    }

}

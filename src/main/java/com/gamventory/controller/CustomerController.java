package com.gamventory.controller;

import com.gamventory.dto.NoticeFormDto;
import com.gamventory.dto.NoticeListDto;
import com.gamventory.dto.NoticeSearchDto;
import com.gamventory.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
@Log4j2
public class CustomerController {

    private final NoticeService noticeService;

    /**
     * CustomerController = 고객센터와 관련된 컨트롤러
     * 해당 컨트롤러는 공지사항과 Q&A 게시판 기능을 담당하는 Controller
     * 상단은 공지사항, 하단은 Q&A을 정의
     */

    // 공지사항

    @GetMapping(value = "/notice")
    public String noticeView(NoticeSearchDto noticeSearchDto, Model model, Optional<Integer> page) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<NoticeListDto> noticeListDto = noticeService.getNoticeList(noticeSearchDto, pageable);

        model.addAttribute("noticeListDto", noticeListDto);
        model.addAttribute("noticeSearchDto", noticeSearchDto);
        model.addAttribute("maxPage", 5);

        return "customer/noticeList";
    }

    @GetMapping(value = "/notice/{id}")
    public String noticeDetailView(@RequestParam("id") String id, Model model){

        return "customer/noticeDetail";
    }

    @GetMapping(value = "/notice/write")
    public String noticeWriteView(Model model){

        NoticeFormDto noticeFormDto = NoticeFormDto.builder().build();
        model.addAttribute("noticeFormDto", noticeFormDto);

        return "customer/noticeWrite";
    }

    @PostMapping(value = "/notice/write")
    public String noticeWrite(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult, Model model, Principal principal) {

        if(bindingResult.hasErrors()) {
           log.info("Post method -> /notice/write ");
           log.info(bindingResult.toString());
           return "customer/noticeWrite";
        }

        noticeService.saveNotice(noticeFormDto, principal.getName());

        return "redirect:/customer/notice";
    }

    // Q&A

    @GetMapping(value = "/qna")
    public String QnaView() {
        return "customer/qnaList";
    }

}

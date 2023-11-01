package com.gamventory.controller;

import com.gamventory.dto.NoticeFormDto;
import com.gamventory.dto.NoticeListDto;
import com.gamventory.dto.NoticeSearchDto;
import com.gamventory.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
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

//        Page<NoticeListDto> noticeList = noticeService.getList(pageable);

        return "customer/noticeList";
    }

    // Q&A

    @GetMapping(value = "/qna")
    public String QnaView() {
        return "customer/qnaList";
    }

}

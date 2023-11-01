package com.gamventory.service;

import com.gamventory.dto.NoticeFormDto;
import com.gamventory.dto.NoticeListDto;
import com.gamventory.dto.NoticeSearchDto;
import com.gamventory.entity.Member;
import com.gamventory.entity.Notice;
import com.gamventory.repository.MemberRepository;
import com.gamventory.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    private final MemberRepository memberRepository;

    // 공지사항 전체 목록 리스트
    public Page<NoticeListDto> getNoticeList(NoticeSearchDto noticeSearchDto, Pageable pageable) {
        return this.noticeRepository.getSearchNoticePage(noticeSearchDto, pageable);
    }

    public void saveNotice(NoticeFormDto noticeFormDto, String email) {
        Member member = memberRepository.findByEmail(email);
        Notice notice = Notice.builder()
                .subject(noticeFormDto.getSubject())
                .content(noticeFormDto.getContent())
                .member(member)
                .build();
        noticeRepository.save(notice);
    }

}

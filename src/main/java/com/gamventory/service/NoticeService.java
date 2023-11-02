package com.gamventory.service;

import com.gamventory.dto.*;
import com.gamventory.entity.Member;
import com.gamventory.entity.Notice;
import com.gamventory.repository.MemberRepository;
import com.gamventory.repository.NoticeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .viewCount(0L)
                .build();
        noticeRepository.save(notice);
    }

    public NoticeDetailDto getNoticeDetail(Long id) {

        Notice notice = noticeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        NoticeDetailDto noticeDetailDto = NoticeDetailDto.of(notice);

        return noticeDetailDto;
    }

    public NoticeUpdateFormDto getUpdateFormDtoFromNotice(Long id) {

        Notice notice = noticeRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        NoticeUpdateFormDto noticeUpdateFormDto = NoticeUpdateFormDto.builder()
                .id(notice.getId())
                .subject(notice.getSubject())
                .content(notice.getContent())
                .build();

        return noticeUpdateFormDto;
    }

    public void noticeUpdateFormDtoSave(NoticeUpdateFormDto noticeUpdateFormDto) {

        Notice notice = noticeRepository.findById(noticeUpdateFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        notice.modifyUpdate(noticeUpdateFormDto);
        noticeRepository.save(notice);
    }

}

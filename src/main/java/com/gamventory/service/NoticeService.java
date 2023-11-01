package com.gamventory.service;

import com.gamventory.entity.Notice;
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

    // 공지사항 전체 목록 리스트
    public Page<Notice> getList(int page) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("regTime"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        return this.noticeRepository.findAll(pageable);
    }

}

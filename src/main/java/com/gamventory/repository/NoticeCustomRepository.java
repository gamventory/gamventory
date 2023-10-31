package com.gamventory.repository;

import com.gamventory.dto.NoticeListDto;
import com.gamventory.dto.NoticeSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeCustomRepository {

    Page<NoticeListDto> getSearchNoticePage(NoticeSearchDto noticeSearchDto, Pageable pageable);
}

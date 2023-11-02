package com.gamventory.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeListDto {

    private Long id;

    private String subject;

    private String content;

    private Long viewCount;

    private String memberName;

    private LocalDateTime regTime;

    @QueryProjection
    public NoticeListDto(Long id, String subject, String content, Long viewCount, String memberName, LocalDateTime regTime) {

        this.id = id;
        this.subject = subject;
        this.content = content;
        this.viewCount = viewCount;
        this.memberName = memberName;
        this.regTime = regTime;

    }

}

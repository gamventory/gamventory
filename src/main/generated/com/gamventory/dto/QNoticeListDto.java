package com.gamventory.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.gamventory.dto.QNoticeListDto is a Querydsl Projection type for NoticeListDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QNoticeListDto extends ConstructorExpression<NoticeListDto> {

    private static final long serialVersionUID = -599822393L;

    public QNoticeListDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> subject, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<Long> viewCount, com.querydsl.core.types.Expression<String> memberName, com.querydsl.core.types.Expression<java.time.LocalDateTime> regTime) {
        super(NoticeListDto.class, new Class<?>[]{long.class, String.class, String.class, long.class, String.class, java.time.LocalDateTime.class}, id, subject, content, viewCount, memberName, regTime);
    }

}


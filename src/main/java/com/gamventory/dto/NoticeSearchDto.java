package com.gamventory.dto;

import lombok.Data;

@Data
public class NoticeSearchDto {

    // 제목 검색
    private String subjectQuery;

    // 내용 검색
    private String contentQuery;

}

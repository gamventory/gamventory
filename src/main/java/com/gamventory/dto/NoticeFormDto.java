package com.gamventory.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class NoticeFormDto {

    @NotEmpty(message = "제목은 필수항목 입니다.")
    private String subject;

    @NotEmpty(message = "내용은 필수항목 입니다.")
    private String content;

}

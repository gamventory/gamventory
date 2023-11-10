package com.gamventory.dto;

import com.gamventory.entity.Notice;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@Builder
public class NoticeUpdateFormDto {

    private Long id;

    @NotEmpty(message = "제목은 필수항목 입니다.")
    private String subject;

    @NotEmpty(message = "내용은 필수항목 입니다.")
    private String content;
}

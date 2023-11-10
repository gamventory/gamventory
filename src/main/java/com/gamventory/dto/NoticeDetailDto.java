package com.gamventory.dto;

import com.gamventory.entity.Item;
import com.gamventory.entity.Notice;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
public class NoticeDetailDto {

    // 글번호
    private Long id;

    // 제목
    private String subject;

    // 내용
    private String content;


    // 등록 시간
    private LocalDateTime regTime;

    private static ModelMapper modelMapper = new ModelMapper();

    public Notice createItem(){
        return modelMapper.map(this, Notice.class);
    }

    public static NoticeDetailDto of(Notice notice){
        return modelMapper.map(notice,NoticeDetailDto.class);
    }
}

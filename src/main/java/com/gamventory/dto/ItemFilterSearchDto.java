package com.gamventory.dto;

import java.time.LocalDateTime;

import com.gamventory.constant.Category;
import com.gamventory.constant.Platform;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemFilterSearchDto {

    /* 상품 필터검색 dto */

    private Category category;

    private Platform platform;

    // 출시일 시작 범위
    private LocalDateTime releaseDateFrom;  

     // 출시일 끝 범위
    private LocalDateTime releaseDateTo;   
    
    private String Price; 

    private String orderByReleaseDate; 

    
}
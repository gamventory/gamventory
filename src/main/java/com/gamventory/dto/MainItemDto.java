package com.gamventory.dto;

import com.querydsl.core.annotations.QueryProjection;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainItemDto {

    /* 메인 페이지에서 상품을 보여줄 때 사용할 dto */
    
    private Long id;

    private String itemNm;

    private String itemDetail;

    private String imgUrl;

    private Integer price;

    private String platform;

    private String category;

    @QueryProjection
    public MainItemDto(Long id, String itemNm, String itemDetail, String imgUrl, Integer price){
        this.id = id;
        this.imgUrl = imgUrl;
        this.itemDetail = itemDetail;
        this.itemNm = itemDetail;
        this.price = price;
        this.platform = platform;
        this.category = category;
    }

}

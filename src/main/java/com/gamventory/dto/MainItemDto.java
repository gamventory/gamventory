package com.gamventory.dto;

import java.text.NumberFormat;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainItemDto {

    /* 메인 페이지에서 상품을 보여줄 때 사용할 dto */
    
    //고유번호
    private Long id; 

    //제목
    private String itemNm; 

    //상세내용
    private String itemDetail; 

    //이미지 주소
    private String imgUrl; 

    //가격
    private Integer price;

    //플랫폼
    private String platform;

    //카테고리
    private String category;

    //pc,콘솔여부
    private String gameKind;

    @QueryProjection
    public MainItemDto(Long id, String itemNm, String itemDetail, String imgUrl, Integer price, String platform, String category, String gameKind){
        this.id = id;
        this.imgUrl = imgUrl;
        this.itemDetail = itemDetail;
        this.itemNm = itemNm;
        this.price = price;
        this.platform = platform;
        this.category = category;
        this.gameKind = gameKind;
    }

    //숫자포맷으로 설정하는거
    public String getFormattedPrice() {
        NumberFormat formatter = NumberFormat.getInstance();
        return formatter.format(price);
    }

}

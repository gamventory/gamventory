package com.gamventory.dto;

import com.gamventory.constant.Platform;

import lombok.Getter;
import lombok.Setter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailDto {

    //장바구니 조회 페이지에 전달할 DTO클래스

    private Long cartItemId; //장바구니 상품 아이디

    private String itemNm; //상품명

    private Long itemId; //상품 아이디

    private int price; //상품 금액

    private int count; //수량

    private String imgUrl; //상품 이미지 경로

    public CartDetailDto(Long cartItemId, String itemNm, int price, int count, String imgUrl){
        this.cartItemId = cartItemId;
        this.itemId = itemId;
        this.itemNm = itemNm;
        this. price = price;
        this.count = count;
        this.imgUrl = imgUrl;

    }

}


package com.gamventory.dto;

import com.gamventory.entity.OrderItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

    /* 주문 상품 정보를 담을 dto */

    //상품명
    private String itemNm;

    //주문 수량
    private int count;

    //주문 금액
    private int orderPrice;

    //상품 이미지 경로
    private String imgUrl;
    
    public OrderItemDto(OrderItem orderItem, String imgUrl){
        this.itemNm = orderItem.getItem().getItemNm();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }
}


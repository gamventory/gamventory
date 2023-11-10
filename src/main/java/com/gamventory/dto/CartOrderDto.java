package com.gamventory.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartOrderDto {
    //장바구니 상품 주문 dto

    private Long cartItemId;

    //장바구니에서 여러개를 주문하므로 자기자신을 List로 가져옴
    private List<CartOrderDto> cartOrderDtoList;

    
}


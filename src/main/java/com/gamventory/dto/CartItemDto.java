package com.gamventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    
    //상품아이디
    @NotNull(message = "상품 아이디는 필수 입력값입니다.")
    private Long ItemId;

    //장바구니 상품 개수
    @Min(message = "최소 1개 이상 담아주세요", value = 1)
    private int count;

}

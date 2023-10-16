package com.gamventory.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.gamventory.constant.OrderStatus;
import com.gamventory.entity.Order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderHistDto {

    /* 주문 정보를 담을 dto */
    
    //주문 아이디
    private Long orderId;

    //주문 날짜
    private String orderDate;

    //주문 상태
    private OrderStatus orderStatus;

    //주문 상품 리스트
    private List<OrderItemDto> OrderItemDtoList = new ArrayList<>();
    
    //주문 내역 값을 저장하는 메소드
    public OrderHistDto(Order order){
        this.orderId = order.getId();
        this.orderDate =  order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    //주문상품을 리스트로 저장하는 메소드
    public void addOrderItemDto(OrderItemDto orderItemDto){
        OrderItemDtoList.add(orderItemDto);
    }


}
package com.gamventory.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class OrderItem extends BaseEntity{
    
    @Id  @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    //하나의 상품은 여러 주문 상품으로 들어갈 수 있음으로 상품:주문 상품= 1:n
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "item_id")
    private Item item;
  

    //한번의 주문에 여러 상품을 주문해서 주문:주문상품 = 1:n
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "order_id")
    private Order order;

     // 주문가격
    private int orderPrice;

    //수량
    private int count; 

    public static OrderItem createOrderItem(Item item, int count){

        OrderItem orderItem = new OrderItem();
         // 주문할 상품, 수량 세팅
        orderItem.setItem(item);
        orderItem.setCount(count); 
        // 현재 시간 기준으로 상품 가격을 주문 가격으로 세팅, 쿠폰이나 할인 적용시에는 메서드 다른곳에 만든 다음에 여기에 추가해주면 됨
        orderItem.setOrderPrice(item.getPrice()); 

        // 주문 수량 만큼 재고 수량감소시키는 함수
        item.removeStock(count); 
        
        return orderItem;
    }

    //주문한 총 가격을 계산하는 메서드
    public int getTotalPrice(){
        return orderPrice*count; 
    }

    //주문취소시 재고를 늘려주는 메서드
    public void cancel(){
        this.getItem().addStock(count);
    }

}

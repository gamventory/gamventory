package com.gamventory.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import com.gamventory.constant.OrderStatus;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    //한명의 회원이 여러번 주문할 수 있음으로 1:n관계
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "member_id")
    private Member member; 

    //주문일
    private LocalDateTime orderDate; 

     //주문상태
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    //연관관계의 주인이 order, OrderItem에 있는 Order에 의해 관리됨, 영속성 변화 전부 삭제, 고아객체 삭제
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
                orphanRemoval = true, fetch = FetchType.LAZY) 
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime regTime;

    // 주문 수정일
    private LocalDateTime upDateTime; 

    // 주문 정보를 담는 메소드
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //상품을 주문한 회원의 정보를 세팅하는 메소드
    public static Order createOrder(Member member, List<OrderItem> orderItemList){
        Order order = new Order();
        order.setMember(member);

        //상품 페이지에서 1개를 주문하지만 장바구니에서는 여러개를 주문함으로 for문으로 order에 orderItem객체를 넣어줌
        for(OrderItem orderItem : orderItemList){
            order.addOrderItem(orderItem);
        }

        //주문 상태를 ORDER로 셋팅
        order.setOrderStatus(OrderStatus.ORDER); 
        //주문 시간 셋팅
        order.setOrderDate(LocalDateTime.now()); 
        return order;
    }

    //총 주문 금액을 구하는 메소드
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    //주문 취소시 주문 수량을 상품의 재고에 더하고 주문 상태를 취소로 만드는 메서드
    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;

        for(OrderItem orderItem:orderItems){
            orderItem.cancel();
        }
    }
}
package com.gamventory.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.gamventory.dto.OrderDto;
import com.gamventory.dto.OrderHistDto;
import com.gamventory.dto.OrderItemDto;
import com.gamventory.entity.Item;
import com.gamventory.entity.ItemImg;
import com.gamventory.entity.Member;
import com.gamventory.entity.Order;
import com.gamventory.entity.OrderItem;
import com.gamventory.repository.ItemImgRepository;
import com.gamventory.repository.ItemRepository;
import com.gamventory.repository.MemberRepository;
import com.gamventory.repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemImgRepository itemImgRepository;

    //주문 로직 메소드
    public Long order(OrderDto orderDto, String email){

        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        //현재 로그인한 이메일 정보로 회원정보 조회
        Member member = memberRepository.findByEmail(email); 
        
        List<OrderItem> orderItemList = new ArrayList<>();
        //상품과 상품개수를 이용해여 orderItem 객체에 넣어줌
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount()); 
        orderItemList.add(orderItem);

        //회원정보와 상품 리스트 정보를 order에 넣어줌
        Order order = Order.createOrder(member, orderItemList); 
        orderRepository.save(order);

        return order.getId();
        
    }

    //주문 목록을 조회하는 메소드
    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {

        //유저 아이디, 페이징으로 주문목록 조회
        List<Order> orders = orderRepository.findOrders(email, pageable);
        
        // 유저의 총 주문개수
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        //구매이력페이지에 전달할 dto생성
        for (Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            //주문 상품의 대표 이미지 조회
            for (OrderItem orderItem : orderItems) {
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn
                        (orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto =
                        new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }

            orderHistDtos.add(orderHistDto);
        }
        //페이지 구현 객체를 생성해서 반환
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

    //주문 취소하는 메서드, 현재 로그인한 사용자와 주문 데이터가 같은지 검사 후 같으면 true, 다르면 false 반환하는 메서드
    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email){
        
        Member curMember = memberRepository.findByEmail(email);
        
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException:: new);

        Member savedMember = order.getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }
        return true;
    }

    //주문 취소 상태로 변경하면 트랜잭션이 끝날 때 update쿼리가 실행
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }

    //장바구니에서 주문 상품 데이터를 받아 주문을 생성하는 메서드
    public Long orders(List<OrderDto> orderDtoList, String email){
        Member member = memberRepository.findByEmail(email);
        List<OrderItem> orderItemList = new ArrayList<>();

        for(OrderDto orderDto: orderDtoList){
            Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }
}


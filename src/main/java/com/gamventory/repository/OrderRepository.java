package com.gamventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gamventory.entity.Order;


public interface OrderRepository extends JpaRepository<Order, Long>{

        
    //주문 이력을 조회하는 쿼리 +페이징처리
    @Query(" select o from Order o " +
            " where o.member.email = :email " +
            " order by o.orderDate desc"
            )
    List<Order> findOrders(@Param("email") String email, org.springframework.data.domain.Pageable pageable);

    //회원의 주문한 갯수가 몇개인지 조회하는 쿼리
    @Query(" select count(o) from Order o " +
            " where o.member.email = :email")
    Long countOrder(@Param("email") String email);
    
}
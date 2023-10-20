package com.gamventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamventory.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long>{
    
    Cart findByMemberId(Long memberId);
}


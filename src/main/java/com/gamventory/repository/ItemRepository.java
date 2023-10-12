package com.gamventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamventory.exception.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
    
    /* 손원덕 2023-10-12 */
}

package com.gamventory.repository;

import java.util.List;

public interface CartItemCustomRepository {

    //카트아이템 아이디로 아이템아이디 찾는 동적쿼리
    List<Long> findItemIdsByCartId(Long cartId);
    
}

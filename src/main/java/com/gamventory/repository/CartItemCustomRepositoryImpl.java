package com.gamventory.repository;

import java.util.List;

import com.gamventory.entity.QCartItem;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class CartItemCustomRepositoryImpl implements CartItemCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Long> findItemIdsByCartId(Long cartId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QCartItem qCartItem = QCartItem.cartItem;

        return queryFactory
            .select(qCartItem.item.id) // item의 id를 선택합니다.
            .from(qCartItem)
            .where(cartId == null ? qCartItem.cart.id.isNull() : qCartItem.cart.id.eq(cartId)) // cart의 id를 비교합니다.
            .fetch();
    }
}

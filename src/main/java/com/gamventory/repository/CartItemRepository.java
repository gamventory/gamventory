package com.gamventory.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gamventory.dto.CartDetailDto;
import com.gamventory.entity.CartItem;
public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    
    //장바구니에 들어갈 상품을 저장하거나 조회하는 Jpa
    
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    //dto를 반환할 때는 new, DTO패키지, 클래스명을 적어줌, 파라미터 순서는 DTO클래스에 명시한 순으로 넣어줄 것
    @Query(" select new com.gamventory.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl ) "+
    " from CartItem ci, ItemImg im " +
    " join ci.item i " + 
    " where ci.cart.id = :cartId " +
    " and im.item.id = ci.item.id " +
    " and im.repimgYn = 'Y' " +
    " order by ci.regTime desc "
            )
    List<CartDetailDto> findCartDetailDtoList(@Param("cartId") Long cartId);
}

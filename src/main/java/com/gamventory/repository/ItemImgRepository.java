package com.gamventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamventory.entity.ItemImg;


public interface ItemImgRepository extends JpaRepository<ItemImg, Long>{

    /* 아이템 이미지 관련 레포지토리 */

    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

    //주문 상품의 대표이미지를 보여주는 쿼리
    ItemImg findByItemIdAndRepimgYn(Long itemId, String repimgYn);
}

package com.gamventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.gamventory.constant.Category;
import com.gamventory.constant.Platform;
import com.gamventory.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>,QuerydslPredicateExecutor<Item>, ItemRepositoryCustom{

    
    /* Item 테이블의 db를 생성해주는 클래스입니다. */

    //아이템이름으로 아이템 목록을 찾는 쿼리메서드
    List<Item> findByItemNm(String itemNm);
    
    //지정된 가격의보다 작은 금액을 내림차순으로 찾는 쿼리메서드
    List<Item> findByPriceLessThanOrderByPriceDesc(int price);

    Item findByItemNmAndPrice(String itemNm, int price);

    
//     //플랫폼을 기준으로 아이템 목록을 찾는 쿼리메서드
//     List<Item> findByPlatform(Platform platform);
    
//     //장르를 기준으로 아이템 목록을 찾는 쿼리메서드
//     List<Item> findByCategory(Category category);

//     //아이템이름 or 상세설명으로 아이템 목록을 찾는 쿼리메서드
//     List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    // 장르 and 플랫폼으로 아이템 목록을 찾는 쿼리 메서드
    List<Item> findByCategoryAndPlatform(Category category, Platform platform);

    //상품 가격 내림차순으로 조회하는 메서드
    @Query(value="select * from item i where i.item_detail like " +
            "%:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailDesc(@Param("itemDetail") String itemDetail);

    //상품 상세내용을 조회하는 메서드
    @Query(value="select * from item i where i.item_detail like " +
            "%:itemDetail% ", nativeQuery = true)
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    Item findByItemNmAndPrice(String itemNm, int parsedPrice);


}

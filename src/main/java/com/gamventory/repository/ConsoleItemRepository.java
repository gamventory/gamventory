// package com.gamventory.repository;

// import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.querydsl.QuerydslPredicateExecutor;
// import org.springframework.data.repository.query.Param;

// import com.gamventory.constant.Category;
// import com.gamventory.constant.Platform;
// import com.gamventory.entity.ConsoleItem;
// import com.gamventory.entity.Item;

// public interface ConsoleItemRepository extends JpaRepository <ConsoleItem, Long>,QuerydslPredicateExecutor<ConsoleItem> {
    
//     //아이템이름으로 아이템 목록을 찾는 쿼리메서드
//     List<Item> findByItemNm(String itemNm);

//      //아이템이름 or 상세설명으로 아이템 목록을 찾는 쿼리메서드
//     List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

//     //지정된 가격의보다 작은 금액을 내림차순으로 찾는 쿼리메서드
//     List<Item> findByPriceLessThanOrderByPriceDesc(int price);

//     //이름과 가격이 같은 컬럼값 찾는 쿼리메서드
//     Item findByItemNmAndPrice(String itemNm, int price);

//     // 장르 and 플랫폼으로 아이템 목록을 찾는 쿼리 메서드
//     List<Item> findByCategoryAndPlatform(Category category, Platform platform);

//     //상품 가격 내림차순으로 조회하는 메서드
//     @Query(value="select * from item i where i.item_detail like " +
//             "%:itemDetail% order by i.price desc", nativeQuery = true)
//     List<Item> findByItemDetailDesc(@Param("itemDetail") String itemDetail);

//     //상품 상세내용을 조회하는 메서드
//     @Query(value="select * from item i where i.item_detail like " +
//             "%:itemDetail% ", nativeQuery = true)
//     List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

// }

package com.gamventory.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gamventory.constant.Category;
import com.gamventory.constant.ItemSellStatus;
import com.gamventory.constant.Platform;
import com.gamventory.entity.Item;

@SpringBootTest
public class ItemRepositoryTest {

     @Autowired
    ItemRepository itemRepository;

    // @Test
    // @DisplayName("상품 저장 테스트")
    // public void createItemTest(){
    //     Item item = new Item();
    //     item.setItemNm("테스트 상품");
    //     item.setPrice(10000);
    //     item.setItemDetail("테스트 상품 상세 설명");
    //     item.setItemSellStatus(ItemSellStatus.SELL);
    //     item.setStockNumber(100);
    //     item.setRegTime(LocalDateTime.now());
    //     item.setUpdateTime(LocalDateTime.now());
    //     item.setCategory(Category.ALL);
    //     item.setPlatform(Platform.STEAM);
    //     Item savedItem = itemRepository.save(item);
    //     System.out.println(savedItem.toString());
    // }

    // public void createItemList(){
    //     for(int i=1;i<=10;i++){
    //         Item item = new Item();
    //         item.setItemNm("테스트 상품" + i);
    //         item.setPrice(10000 + i);
    //         item.setItemDetail("테스트 상품 상세 설명" + i);
    //         item.setItemSellStatus(ItemSellStatus.SELL);
    //         item.setStockNumber(100); item.setRegTime(LocalDateTime.now());
    //         item.setUpdateTime(LocalDateTime.now());
    //         item.setCategory(Category.ALL);
    //         item.setPlatform(Platform.STEAM);
    //         itemRepository.save(item);
    //     }
    // }

    // @Test
    // @DisplayName("상품명 조회 테스트")
    // public void findByItemNmTest(){
    //     this.createItemList();
    //     List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
    //     for(Item item : itemList){
    //         System.out.println(item.toString());
    //     }
    // }

    // // @Test
    // // @DisplayName("상품명, 상품상세설명 or 테스트")
    // // public void findByItemNmOrItemDetailTest(){
    // //     this.createItemList();
    // //     List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
    // //     for(Item item : itemList){
    // //         System.out.println(item.toString());
    // //     }
    // // }

    // @Test
    // @DisplayName("가격 LessThan 테스트")
    // public void findByPriceLessThanTest(){
    //     this.createItemList();
    //     List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
    //     for(Item item : itemList){
    //         System.out.println(item.toString());
    //     }
    // }

    // @Test
    // @DisplayName("가격 내림차순 조회 테스트")
    // public void findByPriceLessThanOrderByPriceDesc(){
    //     this.createItemList();
    //     List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
    //     for(Item item : itemList){
    //         System.out.println(item.toString());
    //     }
    // }

    // // @Test
    // // @DisplayName("플랫폼 별 찾기 테스트")
    // // public void findByPlatform(){
        
    // //     this.createItemList();
    // //     List<Item> itemList = itemRepository.findByPlatform(Platform.EPIC_GAMES);
    // //     for(Item item : itemList){
    // //         System.out.println(item.toString());
    // //     }
    // // }

    // // @Test
    // // @DisplayName("카테고리 별 찾기 테스트")
    // // public void findByCategory(){

    // //     List<Item> itemList = itemRepository.findByCategory(Category.RPG);
    // //     for(Item item : itemList){
    // //         System.out.println(item.toString());
    // //     }
    // // }

    // // @Test
    // // @DisplayName("장르 and 플랫폼 값이 일치하는 상품 찾기 테스트")
    // // public void findByCategoryAndPlatform(){
    // //     this.createItemList();
    // //     List<Item> itemList = itemRepository.findByCategoryAndPlatform(Category.ALL,Platform.STEAM);
    // //     for(Item item : itemList){
    // //         System.out.println(item.toString());
    // //     }
    // // }

    // @Test
    // @DisplayName("높은 가격순으로 조회하는 테스트")
    // public void findByItemDetailDescTest(){
    //     this.createItemList();
    //     List<Item> itemList = itemRepository.findByItemDetailDesc("테스트 상품 상세 설명");
    //     for(Item item : itemList){
    //         System.out.println(item.toString());
    //     }
    // }

    // @Test
    // @DisplayName("낮은 가격순으로 상품 조회하는 테스트")
    // public void findByItemDetailTest(){
    //     this.createItemList();
    //     List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
    //     for(Item item : itemList){
    //         System.out.println(item.toString());
    //     }
    // }


}

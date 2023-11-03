package com.gamventory.entity;


import com.gamventory.constant.Category;
import com.gamventory.constant.ItemSellStatus;
import com.gamventory.constant.Platform;
import com.gamventory.dto.ItemFormDto;
import com.gamventory.exception.OutOfStockException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item extends BaseEntity{

    /* 상품 정보에 대한 entity입니다
     * 상품코드(pk), 상품명, 가격, 재고수량, 상품상세설명, 상품판매상태, 플랫폼, 장르 컬럼이 있습니다.
     */

    //상품 코드
    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;       

    //상품명
    @Column(nullable = false, length = 50)
    private String itemNm; 

    //가격
    @Column(name="price", nullable = false)
    private int price; 

    //재고수량
    @Column(nullable = false)
    private int stockNumber;

    //상품 상세 설명
    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String itemDetail; 

    //상품 판매 상태
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; 

     //플랫폼
    @Enumerated(EnumType.STRING)
    private Platform platform;

    //장르
    @Enumerated(EnumType.STRING)
    private Category category; 

    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
        if(stockNumber > 0){
            itemSellStatus = ItemSellStatus.SELL;
        }
    }

    //상품을 주문할 경우 재고가 감소하는 메소드
    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber; //현재 재고 - 구매하는 재고 수량
        if (restStock < 0) { //수량 부족할 경우 예외
            throw new OutOfStockException("상품의 재고가 부족합니다. 현재 재고 수량: " + this.stockNumber );
        }else if(restStock == 0){
            itemSellStatus = ItemSellStatus.SOLD_OUT;
        }
        this.stockNumber = restStock; //남은 재고수량값을 할당
    }

    //재고를 늘려주는 메소드
    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
        if(stockNumber > 0){
            itemSellStatus = ItemSellStatus.SELL;
        }
    }
    //판매상태를 정하는 메소드
    public ItemSellStatus determineItemSellStatus() {
        if (this.stockNumber > 0) {
            return ItemSellStatus.SELL;
        } else {
            return ItemSellStatus.SOLD_OUT;
        }
    }


}
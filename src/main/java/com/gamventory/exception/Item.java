package com.gamventory.exception;


import com.gamventory.constant.ItemSellStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity{

    /* 손원덕 2023-10-12 12:20 */

    //상품 코드
    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Column(nullable = false)
    private String itemDetail; 

    //상품 판매 상태
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; 

     //플랫폼
    @Column
    private String platform;

    //장르
    @Column
    private String category; 

    //시리얼번호
    @Column
    private String ceralNumber; 

}
package com.gamventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_img")
@Getter
@Setter
public class ItemImg extends BaseEntity{
    
    /* 상품 이미지 entity 
     *  item : itemImg = 1:n관계
     */

    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //이미지 파일명
    private String imgName; 
    
    //원본 이미지 파일명
    private String oriImgName; 

    //이미지 조회 경로
    private String imgUrl; 

     //대표이미지여부
    private String repimgYn;

    //상품 : 상품이미지 = 1 : n
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "item_id")
    private Item item;

    //상품이미지 수정시 한번에 다른 값을 수정할 필요없게 하는 메서드
    public void updateItemImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this. imgUrl = imgUrl;
    }
    
}


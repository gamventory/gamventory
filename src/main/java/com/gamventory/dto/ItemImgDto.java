package com.gamventory.dto;

import org.modelmapper.ModelMapper;

import com.gamventory.entity.ItemImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemImgDto {
    
   private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;
    
    private static ModelMapper modelMapper = new ModelMapper(); //서로 다른 객체를 매핑해주는 객체 생성

    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg,ItemImgDto.class);
    }
}

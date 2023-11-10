package com.gamventory.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.gamventory.constant.Category;
import com.gamventory.constant.GameKind;
import com.gamventory.constant.ItemSellStatus;
import com.gamventory.constant.Platform;
import com.gamventory.entity.Item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private Platform platform;

    private Category category;

    private GameKind gameKind;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>(); //상품 이미지 저장하는 리스트

    private List<Long> itemImgIds = new ArrayList<>(); // 상품 이미지 아이디 저장하는 리스트

    private static ModelMapper modelMapper = new ModelMapper(); 
    
    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }
    
    public static ItemFormDto of(Item item){
        return modelMapper.map(item,ItemFormDto.class);
    }

}

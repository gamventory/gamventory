package com.gamventory.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gamventory.dto.ItemFilterSearchDto;
import com.gamventory.dto.ItemFormDto;
import com.gamventory.dto.ItemImgDto;
import com.gamventory.dto.ItemSearchDto;
import com.gamventory.dto.MainItemDto;
import com.gamventory.entity.Item;
import com.gamventory.entity.ItemImg;
import com.gamventory.repository.ItemImgRepository;
import com.gamventory.repository.ItemRepository;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ItemService {

    private final ItemRepository itemRepository;

    private final ItemImgService itemImgService;

    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{

        //상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            if(i == 0)
                itemImg.setRepimgYn("Y");
            else
                itemImg.setRepimgYn("N");

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        log.info(item.getId());

        return item.getId();
    }


    //상품 데이터 읽기 전용 설정, 변경감지를 수행하지 않아서 성능이 향샹됨
    @Transactional(readOnly = true) 
    public ItemFormDto getItemDtl(Long itemId){
        //상품의 이미지를 조회
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId); 
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        //조회한 ItemImg 엔티티를 DTO객체로 만들어서 리스트에 추가하는 for문
        for (ItemImg itemImg : itemImgList) { 
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        //상품의 아이디를 통해서 엔티티를 조회, 아이디가 없으면 예외 발생시킴
        Item item = itemRepository.findById(itemId) 
                .orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        log.info("getItemDtl" + itemId );
        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
        //상품 수정
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);
        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            itemImgService.updateItemImg(itemImgIds.get(i),
                    itemImgFileList.get(i));
        }

        return item.getId();
    }

    
    //상품 조회 조건과 페이지정보를 받아서 상품 데이터를 조회하는 메소드
    //데이터 수정이 없음으로 readOnly설정
    @Transactional(readOnly = true) 
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }

    //필터검색 호출하는 메서드
     @Transactional(readOnly = true)
    public Page<Item> filterAndSortItems(ItemFilterSearchDto filterSearchDto, Pageable pageable) {
        return itemRepository.filterItemSort(filterSearchDto, pageable);
    }

}
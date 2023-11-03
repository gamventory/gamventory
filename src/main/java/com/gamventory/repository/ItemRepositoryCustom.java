package com.gamventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gamventory.dto.ItemFilterSearchDto;
import com.gamventory.dto.ItemSearchDto;
import com.gamventory.dto.MainItemDto;
import com.gamventory.entity.Item;

public interface ItemRepositoryCustom {
    
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    
    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
    
    //콘솔 메인아이템 출력
    Page<MainItemDto> getMainItemPageConsole(ItemSearchDto itemSearchDto, Pageable pageable);
    
    //PC게임 메인 아이템 출력
    Page<MainItemDto> getMainItemPagePC(ItemSearchDto itemSearchDto, Pageable pageable);

    // //필터검색
    // Page<Item> filterItemSort(ItemFilterSearchDto filterSearchDto, Pageable pageable);

    //필터검색
    // Page<Item> findByCriteria(ItemFilterSearchDto searchDto, Pageable pageable);
    
    // //필터검색
    // Page<Item> filterItemSort(ItemFilterSearchDto filterSearchDto, Pageable pageable);
    
    Page<MainItemDto> findByCriterias(ItemFilterSearchDto searchDto, Pageable pageable);

}

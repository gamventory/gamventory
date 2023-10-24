package com.gamventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gamventory.dto.ItemSearchDto;
import com.gamventory.dto.MainItemDto;
import com.gamventory.entity.Item;

public interface ItemRepositoryCustom {
    
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}

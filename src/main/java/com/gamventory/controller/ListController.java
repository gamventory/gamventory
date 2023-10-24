package com.gamventory.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gamventory.dto.ItemSearchDto;
import com.gamventory.dto.MainItemDto;
import com.gamventory.entity.Item;
import com.gamventory.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ListController {

    private final ItemService itemService;

    @GetMapping("/list")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){
        
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 20);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items); // 목록의 아이템들
        model.addAttribute("itemSearchDto", itemSearchDto); // 검색조건
        model.addAttribute("maxPage", 5);  // 한페이지당 최대 보여줄 페이지 이동수
        for (MainItemDto item : items) {
            System.out.println("출력시킬 값들");
            System.out.println(item.getCategory());
            System.out.println(item.getPlatform());
            
        }
        return "/list/list";
        
    }
    
}

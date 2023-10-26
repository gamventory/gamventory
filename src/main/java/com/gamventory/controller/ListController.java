package com.gamventory.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.gamventory.dto.ItemFilterSearchDto;
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
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model,ItemFilterSearchDto searchDto){
        
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 20);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);
        
         Page<MainItemDto> item = itemService.findItemsByCriteria(searchDto, pageable);

          model.addAttribute("items", item);
        // model.addAttribute("searchDto", searchDto);  
        // 필터 검색 결과를 모델에 추가
        model.addAttribute("searchResults", searchDto);
        model.addAttribute("searchDto", searchDto);
        //  for (Item item : searchResults.getContent()) {
            // System.out.println("카테고리: " + item.getCategory());
            // System.out.println("플랫폼: " + item.getPlatform());
            // System.out.println("발매일: " + item.getRegTime());
            // System.out.println("가격: " + item.getPrice());
            // System.out.println("가격: " + item.getItemNm());
        // }

        List<String> formattedPrices = items.getContent().stream()
                                   .map(MainItemDto::getFormattedPrice)
                                   .collect(Collectors.toList());

        model.addAttribute("formattedPrices", formattedPrices);
        model.addAttribute("items", items); // 목록의 아이템들
        model.addAttribute("itemSearchDto", itemSearchDto); // 검색조건
        model.addAttribute("maxPage", 5);  // 한페이지당 최대 보여줄 페이지 이동수

        return "/list/list";
    }

    //  @GetMapping("/test")
    // public String searchItems(ItemFilterSearchDto searchDto, Model model, @PageableDefault(size = 20) Pageable pageable) {
    //     Page<Item> searchResults = itemService.searchItems(searchDto, pageable);

    //     // 필터 검색 결과를 모델에 추가
    //     model.addAttribute("searchResults", searchResults);
    //     model.addAttribute("searchDto", searchDto);
    //         // Java 코드로 검색 결과 출력
    //     for (Item item : searchResults.getContent()) {
    //         System.out.println("카테고리: " + item.getCategory());
    //         System.out.println("플랫폼: " + item.getPlatform());
    //         System.out.println("발매일: " + item.getRegTime());
    //         System.out.println("가격: " + item.getPrice());
    //     }


    //     return "list/test"; // 검색 결과를 표시할 뷰 페이지
    // }

    // @GetMapping("/list/filter")
    // public String filterItems(ItemFilterSearchDto filterSearchDto, Optional<Integer> page, Model model) {

    //     Pageable pageable = PageRequest.of(page.orElse(0), 20);
    //     Page<Item> items = itemService.filterAndSortItems(filterSearchDto, pageable);

    //     for (Item item : items.getContent()) {
    //         System.out.println(item);
    //     }

    //     model.addAttribute("items", items); 
    //     model.addAttribute("filterSearchDto", filterSearchDto); 

    //     return "/list/list";
    // }
    
    @GetMapping("/listsearch")
    public String searchItems(ItemFilterSearchDto searchDto, Model model, @PageableDefault(size = 20) Pageable pageable) {
        Page<MainItemDto> items = itemService.findItemsByCriteria(searchDto, pageable);

         // For loop to iterate over each item in the page
    for (MainItemDto item : items) {
        System.out.println("ID: " + item.getId());
        System.out.println("Name: " + item.getItemNm());
        System.out.println("Detail: " + item.getItemDetail());
        System.out.println("Image URL: " + item.getImgUrl());
        System.out.println("Price: " + item.getFormattedPrice());
        System.out.println("Platform: " + item.getPlatform());
        System.out.println("Category: " + item.getCategory());
        System.out.println("-------------------------");
    }

        model.addAttribute("items", items);
        model.addAttribute("searchDto", searchDto);  
        model.addAttribute("maxPage", 5);  // 한페이지당 최대 보여줄 페이지 이동수

        return "list/listsearch"; // 검색 결과를 표시할 뷰 페이지
    }
    
}

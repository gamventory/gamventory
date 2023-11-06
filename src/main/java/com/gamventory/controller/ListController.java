package com.gamventory.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gamventory.dto.ItemFilterSearchDto;
import com.gamventory.dto.ItemSearchDto;
import com.gamventory.dto.MainItemDto;
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

        int maxPage = 5;

        int currentBlock = (items.getNumber() / maxPage) + (items.getNumber() % maxPage == 0 ? 0 : 1);

        int startPage = (currentBlock - 1) * maxPage + 1;
        startPage = Math.max(startPage, 1);
        int endPage = Math.min(startPage + maxPage - 1, items.getTotalPages());



        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                                            .boxed()
                                            .collect(Collectors.toList());

                                             
        List<String> formattedPrices = items.getContent().stream()
                                                  .map(MainItemDto::getFormattedPrice)
                                                  .collect(Collectors.toList());

        model.addAttribute("items", item);
        model.addAttribute("searchResults", searchDto);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("formattedPrices", formattedPrices);
        model.addAttribute("items", items); // 목록의 아이템들
        model.addAttribute("itemSearchDto", itemSearchDto); // 검색조건
        model.addAttribute("pageNumbers", pageNumbers); 
        model.addAttribute("currentBlock", currentBlock); 
        model.addAttribute("startPage", startPage); 
        model.addAttribute("endPage", endPage); 

<<<<<<< HEAD
=======

>>>>>>> sonwonduk
        return "list/list";
    }

    @GetMapping("/listsearch")
    public String searchItems(ItemFilterSearchDto searchDto, Model model, @PageableDefault(size = 20) Pageable pageable ) {
        Page<MainItemDto> items = itemService.findItemsByCriteria(searchDto, pageable);

        int maxPage = 5;

    int currentBlock = (items.getNumber() / maxPage) + (items.getNumber() % maxPage == 0 ? 0 : 1);

    int startPage = (currentBlock - 1) * maxPage + 1;
    startPage = Math.max(startPage, 1);
    int endPage = Math.min(startPage + maxPage - 1, items.getTotalPages());


        // Create a list of page numbers for the current block
        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                                            .boxed()
                                            .collect(Collectors.toList());

        model.addAttribute("items", items);
        model.addAttribute("searchDto", searchDto);  
        model.addAttribute("pageNumbers", pageNumbers); // Add the page numbers list here
        model.addAttribute("currentBlock", currentBlock); // Current block number
        model.addAttribute("startPage", startPage); // Start page number of the current block
        model.addAttribute("endPage", endPage); // End page number of the current block


            return "list/listsearch"; // 검색 결과를 표시할 뷰 페이지
        }
    

    
}

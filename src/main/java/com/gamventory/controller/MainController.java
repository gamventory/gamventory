package com.gamventory.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gamventory.dto.ItemSearchDto;
import com.gamventory.dto.MainItemDto;
import com.gamventory.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

      private final ItemService itemService;
  
    @GetMapping("/")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){
        
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 12);
        Pageable cPageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
        Pageable pPageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);

        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);
        Page<MainItemDto> Citems = itemService.getMainItemPageConsole(itemSearchDto, cPageable);
        Page<MainItemDto> Pitems = itemService.getMainItemPagePC(itemSearchDto, pPageable);


        
        for (MainItemDto Pitem : Pitems) {
            System.out.println("P에서 끌고와졌는지?: "+Pitem.getItemNm());
        }
        for (MainItemDto item : items) {
            System.out.println("i에서 끌고와졌는지?: "+item.getItemNm());
        }
        for (MainItemDto Citem : Citems) {
            System.out.println("c에서 끌고와졌는지?: "+Citem.getItemNm());
        }

        model.addAttribute("Pitems", Pitems); // PC목록의 아이템들
        model.addAttribute("Citems", Citems); // Console목록의 아이템들
        model.addAttribute("items", items); // 전체 목록의 아이템들
        model.addAttribute("itemSearchDto", itemSearchDto); // 검색조건
        model.addAttribute("maxPage", 5);  // 한페이지당 최대 보여줄 페이지 이동수
        return "main";

    }

    //  @Autowired
    // private SeleniumSearchScraper scraperService;

    // @GetMapping("/test")
    // public String test() {
    //     scraperService.scrapeAndSaveGames();  // 스크래핑 메서드 호출
    //     return "/test";
    // }

}

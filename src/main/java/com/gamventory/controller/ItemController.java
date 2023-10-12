package com.gamventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gamventory.dto.ItemFormDto;

@Controller
public class ItemController {

    /*상품의 CRUD에 관련된 컨트롤러입니다. */

    //상품 등록화면으로 이동하는 메서드
    @GetMapping("/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "/item/itemForm";
    }
    
}

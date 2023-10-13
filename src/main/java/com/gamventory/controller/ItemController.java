package com.gamventory.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gamventory.dto.ItemFormDto;
import com.gamventory.service.ItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /*상품의 CRUD에 관련된 컨트롤러입니다. */

    //상품 등록화면으로 이동하는 메서드
    @GetMapping("/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "/item/itemForm";
    }

     //상품등록페이지에서 완료시 메인 페이지로 가는 함수
    @PostMapping(value = "/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, 
                            Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){
        //에러 있으면 작성페이지로                    
        if(bindingResult.hasErrors()){
            return "item/itemForm";
        }
        //이미지가 없으면 다시 작성페이지로
        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/itemForm";
        }
        //상품 저장
        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }
        System.out.println("--------------------regDate값이 왜 null일까, 수정값은 db에 저장되긴 함----------------");
        System.out.println(itemFormDto.createItem());
        System.out.println("------------------------------------");

        return "redirect:/";
    }
    
    
}

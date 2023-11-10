package com.gamventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gamventory.constant.ItemSellStatus;
import com.gamventory.dto.ItemFormDto;
import com.gamventory.dto.ItemSearchDto;
import com.gamventory.entity.Item;
import com.gamventory.service.ItemService;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ItemController {

    private final ItemService itemService;

    /*상품의 CRUD에 관련된 컨트롤러입니다. */

    //상품 등록화면으로 이동하는 메서드
    @GetMapping("admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDto", new ItemFormDto());

        return "item/itemForm";
    }

     //상품등록페이지에서 완료시 메인 페이지로 가는 함수
    @PostMapping(value = "admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, 
                            Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){
        //에러 있으면 작성페이지로                    
        if(bindingResult.hasErrors()){

            List<ObjectError> errors = bindingResult.getAllErrors();
            // List<String> errorMessages = errors.stream()
            //                            .map(ObjectError::getDefaultMessage)
                                    //    .collect(Collectors.toList());
            // model.addAttribute("errorMessages", errorMessages);
            
            return "/item/itemForm";
        }
        //이미지가 없으면 다시 작성페이지로
        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){

            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "/item/itemForm";
        }
        //상품 저장
        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            e.printStackTrace();

            return "/item/itemForm";
        }

        return "redirect:/";
    }

    //상품 수정페이지로 이동하는 메소드, 
    @GetMapping(value="admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {
        
        try {

            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
        } catch (EntityNotFoundException e) {

            // 상품의 객체가 없는 에러가 발생할 경우
            model.addAttribute("errorMessage", "존재하지않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());

            return "/item/itemForm";
        }
        
        return "item/itemForm";
    }

    //상품 수정 완료 후 상세페이지로 이동하는 메소드
    @PostMapping(value = "admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto , BindingResult bindingResult, Model model,
                                 @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){
        
        // log.info("로그로그로그"+itemFormDto.getId());
        // log.info(itemFormDto.getPrice());

        if(bindingResult.hasErrors()){
            return "/item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){

            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/itemForm";
        }

        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");

            return "item/itemForm";
        }

        return "redirect:/";
    }

    //관리자 페이지 목록 
    @GetMapping(value = {"admin/items", "admin/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){

        //조회할 페이지번호, 한번에 가지고올 데이터 수
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);


        List<ItemSellStatus> itemSellStatuses = items.getContent()
            .stream()
            .map(Item::determineItemSellStatus)
            .collect(Collectors.toList());

        model.addAttribute("itemSellStatuses", itemSellStatuses);
        //조회한 상품 데이터 및 페이징정보
        model.addAttribute("items", items);
        //페이지 전환시 기존 검색조건을 유지한채 이동
        model.addAttribute("itemSearchDto", itemSearchDto);
        //maxPage 페이지 이동 번호 보여줄 개수
        model.addAttribute("maxPage", 5);

        return "item/itemMng";
    }

     //상세보기 페이지로 이동하는 getmapping
     @GetMapping(value="/item/{itemId}")
     public String itemDtl(Model model, @PathVariable("itemId") Long itemId) {

        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        ItemSellStatus status = (itemFormDto.getStockNumber() > 0) ? ItemSellStatus.SELL : ItemSellStatus.SOLD_OUT;
        
        model.addAttribute("itemSellStatusSell", status);
        model.addAttribute("item", itemFormDto);

        return "item/itemDtl";
     }

     @PostMapping(value = "/item/{itemId}")
     public String itemOrder(@PathVariable("itemId") Long itemId, BindingResult bindingResult, Principal principal){

        if(bindingResult.hasErrors()){

            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

        }

        return "order/order";
     }
    
}

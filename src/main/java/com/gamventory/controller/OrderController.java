package com.gamventory.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamventory.dto.ItemFormDto;
import com.gamventory.dto.OrderDto;
import com.gamventory.dto.OrderHistDto;
import com.gamventory.entity.Member;
import com.gamventory.entity.Serial;
import com.gamventory.repository.MemberRepository;
import com.gamventory.repository.OrderRepository;
import com.gamventory.service.ItemService;
import com.gamventory.service.OrderService;
import com.gamventory.service.SerialService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final SerialService serialService;
    private final MemberRepository memberRepository;

    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long orderid;
        
        try {
            orderService.updateSerialUserStatus(orderDto, principal.getName()); 
            orderid = orderService.order(orderDto, email);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(orderid, HttpStatus.OK);
    }


    @PostMapping(value = "/order/{itemId}")
    public @ResponseBody ResponseEntity order(@PathVariable Long itemId, @RequestBody @Valid OrderDto orderDto, BindingResult bindingResult, Principal principal){

        if(bindingResult.hasErrors()){

            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);

        }

        String email = principal.getName();
        Long orderid;
        
        try {
            orderid = orderService.order(orderDto, email);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderid, HttpStatus.OK);

    }

    //구매내역페이지로 이동하는 메서드
    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model){

        //한번에 가지고 올 주문의 개수는 8개로 설정
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<OrderHistDto> ordersHistDtoList = orderService.getOrderList(principal.getName(), pageable);
        Member member = memberRepository.findByEmail(principal.getName());
        if (member == null) {
            // 적절한 오류 처리를 수행합니다.
            return "/"; 
        }
        String loggedInEmail = principal.getName();
        Page<Serial> serials = serialService.getSerialsByMemberEmail(loggedInEmail, pageable);
        
        for (Serial serial : serials) {
            System.out.println(serial);
        }
        for (OrderHistDto orderHistDto : ordersHistDtoList) {
            System.out.println(orderHistDto.getOrderId());  // 여기에서는 단순히 toString() 결과를 출력하도록 했습니다.
            System.out.println(orderHistDto.getOrderStatus());  // 여기에서는 단순히 toString() 결과를 출력하도록 했습니다.
        }

        for (Serial serial : serials) {
            System.out.println("Serial Number: " + serial.getSerialNumber());
            System.out.println("ID: " + serial.getId());
            System.out.println("User Status: " + serial.isUserStatus());
            System.out.println("------------------------");
        }

        model.addAttribute("serials", serials);
        model.addAttribute("orders", ordersHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "order/orderHist";
    }

     //주문 취소시 발생하는 메서드
     @PostMapping("/order/{orderId}/cancel")
     public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId, Principal principal){
 
         //자바스크립트에서 취소할 주문 번호는 조작이 가능함으로 주문 취소하지 못하도록 권한 검사
         if(!orderService.validateOrder(orderId, principal.getName())){
             return new ResponseEntity<String>("주문 취소 권한이 없습니다.",HttpStatus.FORBIDDEN);
         }
         //주문 취소로직 호출
         orderService.cancelOrder(orderId);
         return new ResponseEntity<Long>(orderId, HttpStatus.OK);
     }

     //구매페이지
    @GetMapping(value="/order/{itemId}")
    public String orderPage(Model model,  @PathVariable("itemId") Long itemId, Principal principal) {
             
         ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
         String email = principal.getName();
         System.out.println(email+"이메일아이디값임");


        model.addAttribute("item", itemFormDto);
        model.addAttribute("email", email);


        return "order/order";
    }


    //결제완료 페이지
    @GetMapping(value = "/orderEnd")
    public String orderEnd(){
        return "order/orderEnd";
    }

}

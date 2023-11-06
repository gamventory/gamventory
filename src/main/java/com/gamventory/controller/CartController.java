package com.gamventory.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamventory.dto.CartDetailDto;
import com.gamventory.dto.CartItemDto;
import com.gamventory.dto.CartOrderDto;
import com.gamventory.dto.OrderDto;
import com.gamventory.service.CartService;
import com.gamventory.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {
    
    private final CartService cartService;
    private final OrderService orderService;

    //장바구니에 요청하는 메서드 처리하는 함수
    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult, Principal principal){
        //장바구니 바인딩시 에러 체크
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(),HttpStatus.BAD_REQUEST);
        }

        //로그인한 정보 email에 저장
        String email = principal.getName();
        Long cartItemId;

        //화면으로 넘어온 장바구니에 담을 상품 정보와 현재 로그인한 회원 이메일 정보를 이용해서 장바구니 상품을 담는 로직 호출
        try {
            cartItemId = cartService.addCart(cartItemDto, email);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    //장바구니항목 구매 페이지 로딩하는 메서드
    @GetMapping(value = "/cart/order")
    public String orderHist(Principal principal, Model model){

        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartDetailList);
        
        return "/cart/cartOrder";
    }

    //장바구니의 수량을 변경하는 메서드
    @PatchMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity  updateCartItem(@PathVariable("cartItemId") Long cartItemId, int count, Principal principal){
        if(count <= 0){
            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        }else if (!cartService.validateCartItem(cartItemId, principal.getName())){
            return new ResponseEntity<String>("수정권한이 없습니다.",HttpStatus.FORBIDDEN); 
        }

        cartService.updateCartItemCount(cartItemId, count);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    //장바구니의 목록을 삭제하는 메서드
    @DeleteMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal){

        if(!cartService.validateCartItem(cartItemId, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.deleteCartItem(cartItemId);

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    //장바구니 상품의 수량을 업데이트하는 요청을 처리하는 메서드
    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity orderCartItem(@RequestBody CartOrderDto cartOrderDto, Principal principal){

        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();
        

        //주문할 상품을 선택하지 않았는지 체크
        if(cartOrderDtoList == null || cartOrderDtoList.size() == 0){
            return new ResponseEntity<String>("주문할 상품을 선택해주세요", HttpStatus.FORBIDDEN);
        }
        //주문 권한 체크
        for (CartOrderDto cartOrder : cartOrderDtoList) {
            if(!cartService.validateCartItem(cartOrder.getCartItemId(), principal.getName())){
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }
        }
        // Serial User Status 업데이트
        orderService.updateSerialUserStatusByCartId(cartOrderDto.getCartItemId());
        System.out.println("카트오더디티오의 아이디값은??????"+cartOrderDto.getCartItemId());
        // CartOrderDto를 OrderDto로 변환하여 Serial User Status를 업데이트
        List<OrderDto> orderDtoList = cartOrderDtoList.stream()
        .map(cartOrder -> {
            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartOrder.getCartItemId());
            orderDto.setCount(cartOrderDtoList.size()); // Using the size of the list as count
            return orderDto;
        })
        .collect(Collectors.toList());
        
            
        //주문 로직 호출결과 생성된 주문 번호를 반호나 받음
        Long orderId = cartService.orderCartItem(cartOrderDtoList, principal.getName());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
    

    //장바구니 이동 메서드
       @GetMapping(value = "/cart")
    public String cartList(Principal principal, Model model){

        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartDetailList);
        return "/cart/cartList";
    }

}

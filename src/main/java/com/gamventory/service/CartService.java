// package com.gamventory.service;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import org.thymeleaf.util.StringUtils;

// import com.gamventory.dto.CartDetailDto;
// import com.gamventory.dto.CartItemDto;
// import com.gamventory.dto.CartOrderDto;
// import com.gamventory.dto.OrderDto;
// import com.gamventory.entity.Cart;
// import com.gamventory.entity.CartItem;
// import com.gamventory.entity.Item;
// import com.gamventory.entity.Member;
// import com.gamventory.repository.CartItemRepository;
// import com.gamventory.repository.CartRepository;
// import com.gamventory.repository.ItemRepository;
// import com.gamventory.repository.MemberRepository;

// import jakarta.persistence.EntityNotFoundException;
// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// @Transactional
// public class CartService {
    
//     private final ItemRepository itemRepository;
//     private final MemberRepository memberRepository;
//     private final CartRepository cartRepository;
//     private final CartItemRepository cartItemRepository;
//     private final OrderService orderService;

//     public Long addCart(CartItemDto cartItemDto, String email){
        
//         //장바구니에 담을 상품 엔티티를 조회
//         Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        
//         //현재 로그인한 회원 엔티티 조회
//         Member member = memberRepository.findByEmail(email);
        
//         //현재 로그인한 회원의 장바구니 조회
//         Cart cart = cartRepository.findByMemberId(member.getId());
//         //상품을 처음으로 장바구니에 담으면 장바구니 엔티티 생성
//         if(cart == null){
//             cart = Cart.createCart(member);
//         }

//         //현재 상품이 장바구니에 이미 들어가 있는지 조회
//         CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

//         //이미 들어간 상품이면 기존 수량에 추가하는 함수 호출
//         //없는 상품이면 장바구니에 상품 추가하는 함수 호출 후 저장
//         if (savedCartItem != null) {
//             savedCartItem.addCount(cartItemDto.getCount());
//             return savedCartItem.getId();
//         } else {
//             CartItem cartItem = CartItem.builder()
//                     .cart(cart)
//                     .item(item)
//                     .count(cartItemDto.getCount())
//                     .build();
//             cartItemRepository.save(cartItem);
//             return cartItem.getId();
//         }
//     }


//     //장바구니 리스트를 반환하는 메서드
//     @Transactional(readOnly = true)
//     public List<CartDetailDto> getCartList(String email){

//         List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

//         Member member = memberRepository.findByEmail(email);

//         //현재 로그인한 회원의 장바구니를 조회
//         Cart cart = cartRepository.findByMemberId(member.getId());
//         if(cart == null){
//             return cartDetailDtoList;
//         }
//         //장바구니에 담긴 상품 정보를 조회
//         cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());

//         return cartDetailDtoList;
//     }

//     //장바구니 상품 수량을 업데이트하는 메서드 자바스크립트로 상품조작을 할 예정으로 저장한 회원이 같은지 검사하는 로직 추가
//     @Transactional
//     public boolean validateCartItem(Long cartItemId, String email){

//         //로그인한 회원 조회
//         Member curMember = memberRepository.findByEmail(email);
//         //장바구니 상품을 저장한 회원 조회
//         CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
//         Member savedMember = cartItem.getCart().getMember();
        
//         //장바구니 저장한 회원과 로그인한 회원이 일치하는지 확인
//         if(!StringUtils.equals(curMember.getEmail(),savedMember.getEmail())){
//             return false;
//         }
//         return true;
//     }

//     //장바구니의 수량을 업데이트하는 메서드
//     public void updateCartItemCount(Long cartItemId, int count){
//         CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);

//         cartItem.updateCount(count);
//     }

//     //장바구니의 상품번호를 받아 아이템을 삭제하는 메서드
//     public void deleteCartItem(Long cartItemId) {
//         CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
//         cartItemRepository.delete(cartItem);
//     }


//     //주문한 상품은 장바구니에서 제거하는 메서드
//      public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email){
//         List<OrderDto> orderDtoList = new ArrayList<>();

//         for (CartOrderDto cartOrderDto : cartOrderDtoList) {
//             CartItem cartItem = cartItemRepository
//                             .findById(cartOrderDto.getCartItemId())
//                             .orElseThrow(EntityNotFoundException::new);

//             OrderDto orderDto = new OrderDto();
//             orderDto.setItemId(cartItem.getItem().getId());
//             orderDto.setCount(cartItem.getCount());
//             orderDtoList.add(orderDto);
//         }

//         Long orderId = orderService.orders(orderDtoList, email);
//         for (CartOrderDto cartOrderDto : cartOrderDtoList) {
//             CartItem cartItem = cartItemRepository
//                             .findById(cartOrderDto.getCartItemId())
//                             .orElseThrow(EntityNotFoundException::new);
//             cartItemRepository.delete(cartItem);
//         }

//         return orderId;
//     }

// }


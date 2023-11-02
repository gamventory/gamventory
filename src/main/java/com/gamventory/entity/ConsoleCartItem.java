// package com.gamventory.entity;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Entity
// @Getter
// @Setter
// @Table(name = "cart_item")
// @Builder
// @AllArgsConstructor
// @NoArgsConstructor
// public class ConsoleCartItem extends BaseEntity{
    

//     @Id
//     @GeneratedValue
//     @Column(name = "cart_item_id")
//     private Long id;

//     //장바구니와 상품 = 1:n 관계
//     @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
//     @JoinColumn(name="cart_id")
//     private Cart cart;

//     //장바구니와 장바구니아이템 = 1:n 관계
//     @ManyToOne(fetch = FetchType.LAZY )
//     @JoinColumn(name = "console_item_id")
//     private ConsoleCartItem consoleCartItem; 

//     private int count; //같은 상품을 장바구니에 몇개 담을지 저장

//     //장바구니에 담을 상품 엔티티를 생성하는 엔티티
//     public static ConsoleCartItem createCartItem(Cart cart, ConsoleCartItem consoleCartItem, int count) {
//         return ConsoleCartItem.builder()
//             .cart(cart)
//             .consoleCartItem(consoleCartItem)
//             .count(count)
//             .build();
//     }

//     //장바구니에 기존에 담긴 상품을 추가로 담을 때 기존 수량에 더 추가할때 사용하는 메서드
//      public void addCount(int count){
//         this.count += count;
//     }

//     public void updateCount(int count){
//         this.count = count;
//     }

// }

// package com.gamventory.entity;


// import groovy.transform.ToString;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.OneToOne;
// import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Entity
// @Table(name = "cart")
// @Getter
// @Setter
// @ToString
// @Builder
// @AllArgsConstructor
// @NoArgsConstructor
// public class ConsoleCart extends BaseEntity{
    

//     @Id
//     @Column(name = "console_cart_id" )
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
    
//     //회원 엔티티와 1:1매핑, 회원당 장바구니는 1개씩 가지고 있음
//     //맴버테이블의 id를 외래키로 가짐
//     @OneToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "member_id")
//     private Member member; 


//     //  // 추가: Cart와 CartItem의 관계 설정
//     // @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
//     // private List<CartItem> cartItems = new ArrayList<>();

//     //장바구니의 현재 사용자 정보를 담는 메서드
//     public static ConsoleCart createCart(Member member){
//         return ConsoleCart.builder()
//             .member(member)
//             .build();
//     }

// }

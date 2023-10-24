package com.gamventory.entity;


import com.gamventory.constant.Platform;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="serial")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Serial {
    
    /* 상품의 시리얼 번호에 대한 entity 
     * 시리얼아이디, 시리얼번호, 플랫폼 으로 구성
    */

     @Id
    @Column(name="serial_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;       

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    //시리얼 번호
    @Column(name = "serial_number", nullable = false)
    private String serialNumber; 
   
    //플랫폼
    @Enumerated(EnumType.STRING)
    private Platform platform;

    //발급여부
    @Column(name = "user_status", nullable = false)
    private boolean userStatus;

    //한명의 회원이 여러개 시리얼 번호를 가짐.
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "member_id")
    private Member member; 


    public static Serial createWithRandomSerialNumber(Long itemId, Platform platform, boolean userStatus) {
        return Serial.builder()
                     .itemId(itemId)
                     .serialNumber(UUID.randomUUID().toString())
                     .platform(platform)
                     .userStatus(userStatus) 
                     .build();
    }


    

    
    
}

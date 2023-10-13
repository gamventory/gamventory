package com.gamventory.entity;

import com.gamventory.constant.Platform;

import groovy.transform.ToString;
import groovy.transform.builder.Builder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="serial")
@Getter
@Setter
@ToString
public class Serial {
    
    /* 상품의 시리얼 번호에 대한 entity 
     * 시리얼아이디, 시리얼번호, 플랫폼 으로 구성
    */

     @Id
    @Column(name="serial_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;       

    //시리얼 번호
    @Column(name = "serial_number", nullable = false)
    private String serialNumber; 
   
    //플랫폼
    @Enumerated(EnumType.STRING)
    private Platform platform; 
    
}

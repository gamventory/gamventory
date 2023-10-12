package com.gamventory.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@EntityListeners(value = {AuditingEntityListener.class}) // Auditing 적용을 위한 어노테이션
@MappedSuperclass // 부모 클래스를 상속 받는 자식 클래스에 매핑 정보를 제공
@Getter
@Setter
public abstract class BaseTimeEntity {
    
    // 엔티티 생성 및 저장 -> 시간 자동 저장
    @CreatedDate 
    @Column(updatable = false)
    private LocalDateTime regTime;

    // 변경될때 시간 자동저장
    @LastModifiedDate 
    private LocalDateTime updateTime;

}

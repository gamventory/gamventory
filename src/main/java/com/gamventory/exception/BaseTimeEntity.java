package com.gamventory.exception;


import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@EntityListeners(value = {AuditingEntityListener.class}) //config폴더의 Auditing을 적용하는 어노테이션
@MappedSuperclass //공통 매핑정보가 필요할 때 사용하는 어노테이션, 부모 클래스를 상속받는 자식클래스에게만 매핑정보 제공
@Getter
@Setter
public abstract class BaseTimeEntity {

    /* 손원덕 2023-10-12 */

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

}
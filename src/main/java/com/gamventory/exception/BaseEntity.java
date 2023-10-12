package com.gamventory.exception;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@EntityListeners(value = {AuditingEntityListener.class}) //config폴더의 Auditing을 적용하는 어노테이션
@MappedSuperclass //공통 매핑정보가 필요할 때 사용하는 어노테이션, 부모 클래스를 상속받는 자식클래스에게만 매핑정보 제공
@Getter
public abstract class BaseEntity extends BaseTimeEntity{

    /* 손원덕 2023-10-12 */

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

}
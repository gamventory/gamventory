package com.gamventory.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@EntityListeners(value = {AuditingEntityListener.class}) 
@MappedSuperclass 
@Getter
public abstract class BaseEntity extends BaseTimeEntity{

    /* 공통 entity, 공통시간 entity를 상속받음
     *  작성자, 수정자, 작성일, 수정일 을 가지고 있음
     */

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

}
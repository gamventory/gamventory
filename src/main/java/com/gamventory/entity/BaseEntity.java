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
public abstract class BaseEntity extends BaseTimeEntity {

    /*
     * 상속받은 BaseTimeEntity에서 최초 등록일, 수정일 컬럼을 가지고 있다.
     * 현재 BaseEntity는 작성자, 수정자를 가진 클래스이다.
     */

    // 작성자
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    // 수정자
    @LastModifiedBy
    private String modifiedBy;

}

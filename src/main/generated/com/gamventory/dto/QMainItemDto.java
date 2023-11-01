package com.gamventory.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.gamventory.dto.QMainItemDto is a Querydsl Projection type for MainItemDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMainItemDto extends ConstructorExpression<MainItemDto> {

    private static final long serialVersionUID = 304322225L;

    public QMainItemDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> itemNm, com.querydsl.core.types.Expression<String> itemDetail, com.querydsl.core.types.Expression<String> imgUrl, com.querydsl.core.types.Expression<Integer> price, com.querydsl.core.types.Expression<String> platform, com.querydsl.core.types.Expression<String> category) {
        super(MainItemDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, int.class, String.class, String.class}, id, itemNm, itemDetail, imgUrl, price, platform, category);
    }

}


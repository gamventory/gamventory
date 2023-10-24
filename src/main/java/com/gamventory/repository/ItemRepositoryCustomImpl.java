package com.gamventory.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.gamventory.constant.ItemSellStatus;
import com.gamventory.dto.ItemSearchDto;
import com.gamventory.dto.MainItemDto;
import com.gamventory.dto.QMainItemDto;
import com.gamventory.entity.Item;
import com.gamventory.entity.QItem;
import com.gamventory.entity.QItemImg;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

    /* 상품에 관련된 동적쿼리를 생성하는 querydsl 클래스 */

    //동적으로 쿼리를 생성하는 클래스
    private JPAQueryFactory queryFactory; 

    //JPAQueryFactory의 생성자,em 객체를 넣어줌
    public ItemRepositoryCustomImpl(EntityManager em){  

        this.queryFactory = new JPAQueryFactory(em);
    }

    //상품 판매 상태 조건이 전체가 null 이면 null반환, 아니면 조회된 상품을 조회하는 메소드
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){ 

        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    // 현재시간을 기준으로 dateTime의 값을 이전 시간의 값으로 세팅 후 이후에 등록된 상품만 조회하는 메소드
    private BooleanExpression regDtsAfter(String searchDateType){

        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }

        return QItem.item.regTime.after(dateTime);
    }

    //검색어를 포함하는 상품을 조회하는 메소드
    private BooleanExpression searchByLike(String searchBy, String searchQuery){

        if(StringUtils.equals("itemNm", searchBy)){
            return QItem.item.itemNm.like("%" + searchQuery + "%");
        }  
        else if(StringUtils.equals("createdBy", searchBy)){ 

            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    /*    //쿼리 생성하는 메소드, offset:데이터를 가지고 올 시작 인덱스, limit-한번에 가지고 올 최대개수,
     *    fetchResult - 조회한 리스트, 전체 개수를 포함하는 객체
     */
    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto,Pageable pageable) {

            List<Item> content = queryFactory
                    .selectFrom(QItem.item)
                    .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                            searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                            searchByLike(itemSearchDto.getSearchBy(),
                                    itemSearchDto.getSearchQuery()))
                    .orderBy(QItem.item.id.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            long total = queryFactory.select(Wildcard.count).from(QItem.item)
                    .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                            searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                            searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                    .fetchOne()
                    ;

            return new PageImpl<>(content, pageable, total);
    }


    //메인페이지 작업시 필요한 쿼리
    private BooleanExpression itemNmLike(String searchQuery){

        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        List<MainItemDto> content = queryFactory
                .select(
                        new QMainItemDto(
                                item.id,
                                item.itemNm,
                                item.itemDetail,
                                itemImg.imgUrl,
                                item.price)
                )
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.repimgYn.eq("Y"))
                .where(itemNmLike(itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.repimgYn.eq("Y"))
                .where(itemNmLike(itemSearchDto.getSearchQuery()))
                .fetchOne()
                ;

        return new PageImpl<>(content, pageable, total);
    }

}
package com.gamventory.repository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.compiler.batch.Main;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.gamventory.constant.Category;
import com.gamventory.constant.ItemSellStatus;
import com.gamventory.constant.Platform;
import com.gamventory.dto.ItemFilterSearchDto;
import com.gamventory.dto.ItemSearchDto;
import com.gamventory.dto.MainItemDto;
import com.gamventory.dto.QMainItemDto;
import com.gamventory.entity.Item;
import com.gamventory.entity.QItem;
import com.gamventory.entity.QItemImg;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

    /* 상품에 관련된 동적쿼리를 생성하는 querydsl 클래스 */

    //동적으로 쿼리를 생성하는 클래스
    private JPAQueryFactory queryFactory; 
    private String orderByReleaseDate;

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
                                item.price,
                                item.platform.stringValue(), // Enum to String
                                item.category.stringValue())
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

    // //상품의 카테고리와 일치하는 상품을 조회하는 메소드
    // private BooleanExpression categoryEq(Category category) {
    //     return category == null ? null : QItem.item.category.eq(category);
    // }

    // //상품의 플랫폼과 일치하는 상품을 조회하는 메소드
    // private BooleanExpression platformEq(Platform platform) {
    //     return platform == null ? null : QItem.item.platform.eq(platform);
    // }

    // //상품의 출시일이 특정 날짜 이후인 상품을 조회하는 메소드
    // private BooleanExpression releaseDateAfter(LocalDateTime releaseDateFrom) {
    //     return releaseDateFrom == null ? null : QItem.item.regTime.goe(releaseDateFrom);
    // }

    // //상품의 출시일이 특정 날짜 이전인 상품을 조회하는 메소드
    // private BooleanExpression releaseDateBefore(LocalDateTime releaseDateTo) {
    //     return releaseDateTo == null ? null : QItem.item.regTime.loe(releaseDateTo);
    // }

    // private OrderSpecifier<?>[] getOrderSpecifier(ItemFilterSearchDto filterSearchDto) {
    //     QItem item = QItem.item;
    //     List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
    
    //     if ("ASC".equalsIgnoreCase(filterSearchDto.getPrice())) {
    //         orderSpecifiers.add(item.price.asc());
    //     } else if ("DESC".equalsIgnoreCase(filterSearchDto.getPrice())) {
    //         orderSpecifiers.add(item.price.desc());
    //     }
    
    //     if ("ASC".equalsIgnoreCase(filterSearchDto.getOrderByReleaseDate())) {
    //         orderSpecifiers.add(item.regTime.asc());
    //     } else if ("DESC".equalsIgnoreCase(filterSearchDto.getOrderByReleaseDate())) {
    //         orderSpecifiers.add(item.regTime.desc());
    //     }
    
    //     return orderSpecifiers.toArray(new OrderSpecifier[0]);
    // }

    // //필터검색 쿼리
    // @Override
    // public Page<Item> filterItemSort(ItemFilterSearchDto filterSearchDto, Pageable pageable) {
    //     QItem item = QItem.item;

    //     List<Item> content = queryFactory
    //             .selectFrom(item)
    //             .where(
    //                 categoryEq(filterSearchDto.getCategory()),
    //                 platformEq(filterSearchDto.getPlatform()),
    //                 releaseDateAfter(filterSearchDto.getReleaseDateFrom()),
    //                 releaseDateBefore(filterSearchDto.getReleaseDateTo())
    //             )
    //             .orderBy(getOrderSpecifier(filterSearchDto))
    //             .offset(pageable.getOffset())
    //             .limit(pageable.getPageSize())
    //             .fetch();

    //     long total = queryFactory
    //             .selectFrom(item)
    //             .where(
    //                 categoryEq(filterSearchDto.getCategory()),
    //                 platformEq(filterSearchDto.getPlatform()),
    //                 releaseDateAfter(filterSearchDto.getReleaseDateFrom()),
    //                 releaseDateBefore(filterSearchDto.getReleaseDateTo())
    //             )
    //             .fetchCount();

    //     return new PageImpl<>(content, pageable, total);
    // }


    //     this.queryFactory = queryFactory;
    // }

    @Override
    public Page<MainItemDto> findByCriterias(ItemFilterSearchDto searchDto, Pageable pageable) {
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        BooleanBuilder builder = new BooleanBuilder();

        if (searchDto.getCategory() != null) {
            builder.and(item.category.eq(searchDto.getCategory()));
        }

        if (searchDto.getPlatform() != null) {
            builder.and(item.platform.eq(searchDto.getPlatform()));
        }

        // 정렬 로직
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        if ("asc".equalsIgnoreCase(searchDto.getOrderByReleaseDate())) {
            orderSpecifiers.add(item.regTime.asc());
        } else if ("desc".equalsIgnoreCase(searchDto.getOrderByReleaseDate())) {
            orderSpecifiers.add(item.regTime.desc());
        }

        if ("asc".equalsIgnoreCase(searchDto.getPrice())) {
            orderSpecifiers.add(item.price.asc());
        } else if ("desc".equalsIgnoreCase(searchDto.getPrice())) {
            orderSpecifiers.add(item.price.desc());
        }

        List<MainItemDto> content = queryFactory
            .select(new QMainItemDto(
                item.id,
                item.itemNm,
                item.itemDetail,
                itemImg.imgUrl,
                item.price,
                item.platform.stringValue(),
                item.category.stringValue()
            ))
            .from(itemImg)
            .join(itemImg.item, item)
            .where(itemImg.repimgYn.eq("Y")) // This assumes that only the representative images are considered
            .where(builder)
            .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = queryFactory
            .selectFrom(itemImg)
            .join(itemImg.item, item)
            .where(itemImg.repimgYn.eq("Y"))
            .where(builder)
            .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

        
}
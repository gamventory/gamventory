package com.gamventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.gamventory.entity.QSerial;
import com.gamventory.entity.Serial;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class SerialCustomRepositoryImpl implements SerialCustomRepository {
    private final JPAQueryFactory queryFactory;

    public SerialCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //시리얼 목록을 찾는 리스트 동적쿼리
    @Override
    public Page<Serial> findByKeyword(String keyword, Pageable pageable) {
        QSerial serial = QSerial.serial;

        List<Serial> serials = queryFactory.selectFrom(serial)
                                           .leftJoin(serial.member)
                                           .where(serial.id.stringValue().contains(keyword)
                                                  .or(serial.member.email.contains(keyword)))
                                           .offset(pageable.getOffset())
                                           .limit(pageable.getPageSize())
                                           .fetch();

        Optional<Long> result = Optional.ofNullable(
           queryFactory
                    .select(serial.count())
                    .from(serial)
                    .leftJoin(serial.member)
                    .where(serial.id.stringValue().contains(keyword)
                            .or(serial.member.email.contains(keyword)))
                    .fetchOne());

            long total = result.orElse(0L);
            return new PageImpl<>(serials, pageable, total);
        }
}

package com.gamventory.repository;

import com.gamventory.entity.QSerial;
import com.gamventory.entity.Serial;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import jakarta.persistence.EntityManager;

public class SerialCustomRepositoryImpl implements SerialCustomRepository {
    private final JPAQueryFactory queryFactory;

    public SerialCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //시리얼 목록을 찾는 리스트 동적쿼리
    @Override
    public List<Serial> findByKeyword(String keyword) {
        QSerial serial = QSerial.serial;
        return queryFactory.selectFrom(serial)
                           .leftJoin(serial.member)
                           .where(serial.id.stringValue().contains(keyword)
                                  .or(serial.member.email.contains(keyword)))
                           .fetch();
    }
}

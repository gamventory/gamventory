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

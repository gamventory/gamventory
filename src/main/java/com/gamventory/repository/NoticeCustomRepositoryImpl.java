package com.gamventory.repository;

import com.gamventory.dto.NoticeListDto;
import com.gamventory.dto.NoticeSearchDto;
import com.gamventory.dto.QNoticeListDto;
import com.gamventory.entity.QNotice;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Log4j2
public class NoticeCustomRepositoryImpl implements NoticeCustomRepository {

    private final JPAQueryFactory queryFactory;

    public NoticeCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression subjectLike(String subjectQuery) {
        return StringUtils.isEmpty(subjectQuery) ? null : QNotice.notice.subject.like("%" + subjectQuery + "%");
    }

    private BooleanExpression contentLike(String contentQuery) {
        return StringUtils.isEmpty(contentQuery) ? null : QNotice.notice.content.like("%" + contentQuery + "%");
    }
    @Override
    public Page<NoticeListDto> getSearchNoticePage(NoticeSearchDto noticeSearchDto, Pageable pageable) {

        QNotice notice = QNotice.notice;

        List<NoticeListDto> content = queryFactory
                .select(new QNoticeListDto(
                        notice.id,
                        notice.subject,
                        notice.content,
                        notice.viewCount,
                        notice.member.email,
                        notice.regTime
                ))
                .from(notice)
                .where(subjectLike(noticeSearchDto.getSubjectQuery()))
                .where(contentLike(noticeSearchDto.getContentQuery()))
                .orderBy(QNotice.notice.id.desc())
                .limit(pageable.getPageSize())
        Optional<Long> result = Optional.ofNullable(
            queryFactory
                    .select(Wildcard.count)
                    .from(notice)
                    .where(subjectLike(noticeSearchDto.getSubjectQuery()))
                    .where(contentLike(noticeSearchDto.getContentQuery()))
                    .fetchOne()
        );


        long total = result.orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }
}

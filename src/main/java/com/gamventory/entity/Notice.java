package com.gamventory.entity;

import com.gamventory.dto.ItemFormDto;
import com.gamventory.dto.NoticeDetailDto;
import com.gamventory.dto.NoticeUpdateFormDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notice")
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Notice extends BaseEntity {

    /**
     * 공지사항 Entity
     */

    // 번호
    @Id
    @Column(name = "notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(nullable = false, length = 50)
    private String subject;

    // 내용
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 조회수
    private Long viewCount;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void addViewCount(){
        this.viewCount++;
    }

    public void modifyUpdate(NoticeUpdateFormDto noticeUpdateFormDto) {
        this.subject = noticeUpdateFormDto.getSubject();
        this.content = noticeUpdateFormDto.getContent();
    }



}

package com.gamventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gamventory.entity.Member;
import com.gamventory.entity.Serial;

import java.util.List;

public interface SerialRepository extends JpaRepository<Serial, Long>, SerialCustomRepository {

    List<Serial> findByItemId(Long itemId);

    List<Serial> findByItemIdAndUserStatusFalse(Long itemId);

    //회원 아이디값으로 Serial컬럼값리스트를 찾는 쿼리
    List<Serial> findByMember_Id(Long memberId);

    //페이징처리 추가된 시리얼 리스트정보 찾기
    Page<Serial> findAll(Pageable pageable);

    Page<Serial> findByMember(Member member, Pageable pageable);


}

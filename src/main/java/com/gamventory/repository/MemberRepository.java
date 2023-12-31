package com.gamventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamventory.entity.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
  
    Member findByEmail(String email);

    @Query(value = "select * from member m where m.username = :username and m.phoneNumber", nativeQuery = true)
    public Member findByUsername(@Param("") String username, @Param("") String phoneNumber);



}
    
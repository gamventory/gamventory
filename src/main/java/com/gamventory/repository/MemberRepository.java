package com.gamventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamventory.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
  
    Member findByEmail(String email);

}
    
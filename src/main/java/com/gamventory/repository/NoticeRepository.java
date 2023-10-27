package com.gamventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamventory.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}

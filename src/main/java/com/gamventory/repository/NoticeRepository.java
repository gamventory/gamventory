package com.gamventory.repository;

import com.gamventory.dto.NoticeSearchDto;
import com.gamventory.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeCustomRepository {

}

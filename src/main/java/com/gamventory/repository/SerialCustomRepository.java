package com.gamventory.repository;

import com.gamventory.entity.Serial;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SerialCustomRepository {

    public Page<Serial> findByKeyword(String keyword, Pageable pageable);
    
}

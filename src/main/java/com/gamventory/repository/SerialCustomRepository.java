package com.gamventory.repository;

import com.gamventory.entity.Serial;
import java.util.List;

public interface SerialCustomRepository {

    public List<Serial> findByKeyword(String keyword);
    
}

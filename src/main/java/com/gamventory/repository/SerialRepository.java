package com.gamventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamventory.entity.Serial;

import java.util.List;

public interface SerialRepository extends JpaRepository<Serial, Long> {

}

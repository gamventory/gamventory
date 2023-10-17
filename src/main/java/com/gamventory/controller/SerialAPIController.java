package com.gamventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.gamventory.constant.Platform;
import com.gamventory.dto.SerialDto;
import com.gamventory.service.SerialService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
public class SerialAPIController {

    private final SerialService serialService;

    // Serial 조회 액션을 위한 API 매핑
    @GetMapping("/api/serials/get")
    public SerialDto getSerial(@RequestParam Long serialId) {
        return serialService.getSerialById(serialId).orElse(null);
    }
}

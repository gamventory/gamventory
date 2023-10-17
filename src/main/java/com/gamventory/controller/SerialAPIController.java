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

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class SerialAPIController {

    private final SerialService serialService;

    @Autowired
    public SerialAPIController(SerialService serialService) {
        this.serialService = serialService;
    }

    // Serial 생성 액션을 위한 API 매핑
    @PostMapping("/api/serials/create")
    public SerialDto createSerial(@RequestBody SerialDto request) {
        log.info("서비스까지 오는지 테스트" + request);
        return serialService.createSerialForItem(request.getItemId(), request.getPlatform());
    }

    // Serial 조회 액션을 위한 API 매핑
    @GetMapping("/api/serials/get")
    public SerialDto getSerial(@RequestParam Long serialId) {
        return serialService.getSerialById(serialId).orElse(null);
    }
}

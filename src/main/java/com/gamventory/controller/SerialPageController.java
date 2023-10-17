package com.gamventory.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gamventory.constant.Platform;
import com.gamventory.dto.SerialDto;
import com.gamventory.service.SerialService;

import lombok.Data;

@Controller
public class SerialPageController {

    private final SerialService serialService;

    @Autowired
    public SerialPageController(SerialService serialService) {
        this.serialService = serialService;
    }

    // Serial 생성 페이지를 위한 매핑
    @GetMapping("/serials/create")
    public String showCreateSerialPage() {
        return "serial/createSerial";
    }

    // Serial 조회 페이지를 위한 매핑
    @GetMapping("/serials/get")
    public String showGetSerialPage(@RequestParam(required = false) Long serialId, Model model) {
        if (serialId != null) {
            Optional<SerialDto> serial = serialService.getSerialById(serialId);
            if (serial.isPresent()) {
                model.addAttribute("serial", serial.get());
            } else {
                model.addAttribute("error", "Serial not found");
            }
        }
        return "serial/getSerial";
    }

     @PostMapping("/serials/createSerial")
    public String createSerial(@ModelAttribute SerialCreationRequest request, Model model) {
        List<SerialDto> serialDtos = serialService.createSerialForItem(request.getItemId(), request.getPlatform(), request.getNumberOfSerials());
        model.addAttribute("serial", serialDtos);
        return "serial/showSerial";
    }
    @Data
    public class SerialCreationRequest {
        private Long itemId;
        private Platform platform;
        private int numberOfSerials;
    }

    // 시리얼 목록 페이지를 위한 매핑
    @GetMapping("/serials/list")
    public String showSerialList(Model model) {
        List<SerialDto> serials = serialService.getAllSerials();
        model.addAttribute("serials", serials);
        return "serial/showSerial";
    }
}

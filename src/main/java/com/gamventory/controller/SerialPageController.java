package com.gamventory.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gamventory.constant.Platform;
import com.gamventory.dto.SerialDto;
import com.gamventory.entity.Serial;
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
    @GetMapping("/admin/serials/create")
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

    //시리얼 생성 후 조회페이지로 이동하는 메서드
     @PostMapping("/serials/createSerial")
    public String createSerial(@ModelAttribute SerialCreationRequest request, Model model) {
        List<SerialDto> serialDtos = serialService.createSerialForItem(request.getItemId(), request.getPlatform(), request.getNumberOfSerials());
        model.addAttribute("serial", serialDtos);
        return "redirect:/";
    }
    @Data
    public class SerialCreationRequest {
        private Long itemId;
        private Platform platform;
        private int numberOfSerials;
    }

    // 시리얼 전체 목록 페이지를 위한 매핑
    // @GetMapping({"/serials/list", "/serials/list/{page}"})
    // public String showSerialList(@PathVariable("page") Optional<Integer> page, Model model) {

    //     List<SerialDto> serials = serialService.getAllSerials();
    //     model.addAttribute("serials", serials);
    //     return "serial/showSerial";
    // }
    @GetMapping({"/serials/list", "/serials/list/{page}"})
    public String showSerialList(@PathVariable("page") Optional<Integer> page,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                Model model) {

        int pageNumber = page.orElse(1); // 페이지 번호를 가져오고 기본값은 1로 설정합니다.
        int pageSize = 3; // 페이지 크기를 원하는 값으로 설정하세요.

        List<SerialDto> serials;
        Page<Serial> serialPage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            // 키워드가 제공된 경우 검색 기능을 사용
            List<Serial> searchResults = serialService.searchByKeyword(keyword);
            serials = SerialDto.fromEntityList(searchResults);
            model.addAttribute("isSearchResult", true); // 검색 결과인지 여부
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", 1); // 검색 결과는 1페이지로 간주
        } else {
            // 키워드가 제공되지 않았거나 공백인 경우 원래의 로직을 따름
            serialPage = serialService.getAllSerials(PageRequest.of(pageNumber - 1, pageSize));
            serials = SerialDto.fromEntityList(serialPage.getContent());
            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("totalPages", serialPage.getTotalPages());
        }

        for (SerialDto serialDto : serials) {
            System.out.println("시리얼에 뭐들었나 "+serialDto.getMember());
        }

        model.addAttribute("serials", serials);

        return "serial/showSerial";
    }

}

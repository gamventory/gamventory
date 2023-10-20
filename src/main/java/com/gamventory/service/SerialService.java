package com.gamventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


import com.gamventory.constant.Platform;
import com.gamventory.dto.SerialDto;
import com.gamventory.entity.Serial;
import com.gamventory.repository.SerialRepository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Builder
@Log4j2
public class SerialService {

    private final SerialRepository serialRepository;

    public SerialDto createSerial(SerialDto dto) {
        Serial serial = Serial.builder()
                               .itemId(dto.getItemId())
                               .serialNumber(UUID.randomUUID().toString())
                               .build();

        Serial savedSerial = serialRepository.save(serial);
        
        return SerialDto.builder()
                        .serialId(savedSerial.getId())
                        .itemId(savedSerial.getId())
                        .serialNumber(savedSerial.getSerialNumber())
                        .build();
    }

    public SerialDto getSerial(Long id) {
        Serial serial = serialRepository.findById(id).orElseThrow(() -> new RuntimeException("Serial not found"));
        
        return SerialDto.builder()
                        .serialId(serial.getId())
                        .itemId(serial.getItemId())
                        .serialNumber(serial.getSerialNumber())
                        .build();
    }

    //주어진 상품id와 플랫폼에 대한 새로운 시리얼을 생성하고 저장한 뒤 결과를 SerialDto로 반환하는 메서드
    @Transactional
    public List<SerialDto> createSerialForItem(Long itemId, Platform platform, int numberOfSerials) {
        List<SerialDto> serialDtos = new ArrayList<>();
        for (int i = 0; i < numberOfSerials; i++) {
            Serial serial = Serial.createWithRandomSerialNumber(itemId, platform, false); // userStatus를 false로 초기화
            Serial savedSerial = serialRepository.save(serial);
            serialDtos.add(SerialDto.fromEntity(savedSerial));
        }
        return serialDtos;
    }

    //주어진 시리얼 ID에 해당하는 시리얼 정보를 조회하여 SerialDto로 반환
    @Transactional(readOnly = true)
    public Optional<SerialDto> getSerialById(Long serialId) {
        return serialRepository.findById(serialId).map(SerialDto::fromEntity);
    }

     // 모든 시리얼 목록을 가져오는 메서드
    @Transactional(readOnly = true)
    public List<SerialDto> getAllSerials() {
        List<Serial> serialList = serialRepository.findAll();
        return SerialDto.fromEntityList(serialList);
    }


    // updateSerial, deleteSerial, etc...
}

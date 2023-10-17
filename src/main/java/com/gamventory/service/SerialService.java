package com.gamventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.Optional;

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
    public SerialDto createSerialForItem(Long itemId, Platform platform) {
        Serial serial = Serial.createWithRandomSerialNumber(itemId, platform);
        log.info("서비스저장여부 테스트" + serial);
        Serial savedSerial = serialRepository.save(serial);
        return SerialDto.fromEntity(savedSerial);
    }

    //주어진 시리얼 ID에 해당하는 시리얼 정보를 조회하여 SerialDto로 반환
    @Transactional(readOnly = true)
    public Optional<SerialDto> getSerialById(Long serialId) {
        return serialRepository.findById(serialId).map(SerialDto::fromEntity);
    }

    // updateSerial, deleteSerial, etc...
}

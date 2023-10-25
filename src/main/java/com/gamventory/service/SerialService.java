package com.gamventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


import com.gamventory.constant.Platform;
import com.gamventory.dto.OrderDto;
import com.gamventory.dto.SerialDto;
import com.gamventory.entity.Member;
import com.gamventory.entity.Serial;
import com.gamventory.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public SerialDto createSerial(SerialDto dto,String email) {

        Member member = memberRepository.findByEmail(email); 
        System.out.println("생성시 시리얼맴버번호: ----------" + member);

        Serial serial = Serial.builder()
                               .itemId(dto.getItemId())
                               .serialNumber(UUID.randomUUID().toString())
                               .member(member)
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

    //페이징 처리된 시리얼 목록을 가져오는 메서드
    public Page<Serial> getAllSerials(Pageable pageable) {
        return serialRepository.findAll(pageable);
    }

    public List<SerialDto> getAllSerialDtos(Pageable pageable) {
        Page<Serial> serialPage = serialRepository.findAll(pageable);
        return SerialDto.fromEntityList(serialPage.getContent());
    }


    //회원번호로 Serial값목록을 찾는 메서드
    public List<Serial> getSerialsByMemberId(Long memberId) {
        return serialRepository.findByMember_Id(memberId);
    }

    //사용자 이메일이나 시리얼 ID로 시리얼목록을 조회하는 메서드
    public Page<Serial> searchByKeyword(String keyword, Pageable pageable) {
        return serialRepository.findByKeyword(keyword, pageable);
    }

    public List<Serial> getSerialsByMemberEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            return serialRepository.findByMember(member);
        }
        return new ArrayList<>();
    }
}

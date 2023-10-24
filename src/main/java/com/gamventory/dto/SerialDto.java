package com.gamventory.dto;

import com.gamventory.constant.Platform;
import com.gamventory.entity.Member;
import com.gamventory.entity.Serial;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
import java.util.stream.Collectors;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SerialDto {

    /* 시리얼 번호를 담는 dto */

    private Long serialId;

    private Long itemId;

    private String serialNumber;

    private Platform platform;

    private boolean userStatus;

    private Member member;


    //Serial Entity 인스턴스를 받아서 Dto객체를 반환하는 메서드
    public static SerialDto fromEntity(Serial serial) {

        return SerialDto.builder()
                        .serialId(serial.getId())
                        .itemId(serial.getItemId())
                        .serialNumber(serial.getSerialNumber())
                        .platform(serial.getPlatform())
                        .member(serial.getMember())
                        .build();
    }

    // SerialDto 객체의 정보를 바탕으로 Entity를 반환하는 메서드
    public Serial toEntity() {

        return Serial.builder()
                     .id(this.serialId)
                     .itemId(this.itemId)
                     .serialNumber(this.serialNumber)
                     .platform(this.platform)
                     .member(member)
                     .build();
    }

    public static List<SerialDto> fromEntityList(List<Serial> serialList) {
        return serialList.stream()
                .map(SerialDto::fromEntity)
                .collect(Collectors.toList());
    }
    
    
}

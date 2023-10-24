package com.gamventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberUpdateFormDto {

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    @NotEmpty(message = "이메일 주소??")
    private String email;

    @NotEmpty(message = "주소지 검색 버튼을 통해 주소를 입력해 주세요.")
    private String zipCode;

    private String streetAddress;

    private String detailAddress;

}

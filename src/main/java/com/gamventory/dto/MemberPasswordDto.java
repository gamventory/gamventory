package com.gamventory.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class MemberPasswordDto {

    @NotEmpty(message = "기존의 비밀번호를 입력해 주세요.")
    private String password;

    @NotEmpty(message = "새로운 비밀번호를 입력해주세요.")
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
    private String newPassword;

    @NotEmpty(message = "새로운 비밀번호를 확인해주세요.")
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
    private String newConfirmPassword;

}

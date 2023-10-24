package com.gamventory.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class MemberDeleteForm {

    @NotEmpty(message = "비밀번호를 입력해 주세요.")
    private String password;
}

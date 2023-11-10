package com.gamventory.validation;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AnswerForm {

    @NotEmpty(message = "내용은 필수 입니다.")
    private String content;
}

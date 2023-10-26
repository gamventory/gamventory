package com.gamventory.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberFindDto {
    
    private String email;

}

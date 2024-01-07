package com.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdTokenDto {

    private String idToken;

    public static IdTokenDto of(String idToken){
        return IdTokenDto.builder()
                .idToken(idToken)
                .build();
    }
}
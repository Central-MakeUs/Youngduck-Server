package com.example.api.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateUserInfoRequest {
    @Schema(defaultValue = "닉네임", description = "닉네임")
    private String nickname;
}
package com.example.api.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DuplicateCheckResponse {
    @Schema(defaultValue = "true", description = "중복 여부")
    private boolean isDuplicate;

    @Builder
    public DuplicateCheckResponse(boolean isDuplicate) {
        this.isDuplicate = isDuplicate;
    }

    public static DuplicateCheckResponse from(boolean isDuplicate) {
        return DuplicateCheckResponse.builder()
                .isDuplicate(isDuplicate)
                .build();
    }
}

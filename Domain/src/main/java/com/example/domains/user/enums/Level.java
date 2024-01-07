package com.example.domains.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Level {
    LEVEL_ONE("1"),
    // 탈퇴한유저
    LEVEL_TWO("2");

    private String value;
}

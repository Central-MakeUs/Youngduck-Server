package com.example.domains.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Level {
    ONE("LEVEL_ONE"),
    // 탈퇴한유저
    TWO("LEVEL_TWO");

    private String value;
}

package com.example.domains.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserState {
    ACTIVE("ACTIVE"),
    // 탈퇴한유저
    DELETED("DELETED");

    private String value;
}
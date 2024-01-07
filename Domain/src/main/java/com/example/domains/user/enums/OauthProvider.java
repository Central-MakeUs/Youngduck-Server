package com.example.domains.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum OauthProvider {
    KAKAO("KAKAO"),
    APPLE("APPLE");

    @JsonValue
    private String value;

    @JsonCreator
    public static OauthProvider parsing(String inputValue) {
        return Stream.of(OauthProvider.values())
                .filter(oauthProvider -> oauthProvider.getValue().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
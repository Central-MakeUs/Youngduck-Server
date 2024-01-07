package com.example.domains.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Genre {
    MELLO("멜로"),
    COMEDY("코미디"),
    ROCO("로맨틱코미디"),
    ACTION("액션"),
    WEST("서부극"),
    GANG("갱스터"),
    NOIRE("누아르"),
    SUSPENSE("미스터리"),
    THRILLER("스릴러"),
    HORROR("공포"),
    WAR("전쟁"),
    SF("SF"),
    DETECTIVE("참정"),
    FANTASY("판타지"),
    ADVENTURE("모험");

    @JsonValue private String value;

    @JsonCreator
    public static OauthProvider parsing(String inputValue) {
        return Stream.of(OauthProvider.values())
                .filter(category -> category.getValue().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}

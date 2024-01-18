package com.example.domains.screening.enums;

import com.example.domains.user.enums.OauthProvider;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum Category {
    GRADUATE("졸업상영"),
    ASSIGNMENT("과제상영"),
    CASUAL("정기상영"),
    SPECIAL("특별상영"),
    ETC("기타");


    private String value;
    @JsonCreator
    public static Category parsing(String inputValue) {
        return Stream.of(Category.values())
                .filter(category -> category.getValue().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}

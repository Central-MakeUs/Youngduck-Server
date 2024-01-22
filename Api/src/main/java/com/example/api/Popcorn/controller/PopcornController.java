package com.example.api.Popcorn.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/popcorn")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "access-token")
public class PopcornController {
    //TODO 2. 팝콘작 추천하기 - 예비팝콘 - movieId로 분별
    //TODO 3. 팝콘작 추천된거 보여주는 list 반환
    //TODO 지난주 (테스트는 3분) 팝콘작 중 투표수 가장 높았던 것 3개 반환
    //TODO 4. 예비 팝콘작 투표하기
    //TODO 이번 주 (테스트는 3분) 팝콘작 선정 된 것  저장 -> 스케쥴링? PopCorn
    //TODO 5. 팝콘작 리뷰하기
    //TODO 6. 팝콘작 팝콘지수 산출 - 스케쥴링
    //TODO 1. 독립영화 Top5 매 주 월요일 저장 - 스케쥴링
}

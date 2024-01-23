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

    //TODO 지난주 (테스트는 3분) 팝콘작 중 투표수 가장 높았던 것 3개 반환
    //TODO 이번 주 (테스트는 3분) 팝콘작 선정 된 것  저장 -> 스케쥴링? PopCorn
    //TODO 5. 팝콘작 리뷰하기
    //TODO 6. 팝콘작 팝콘지수 산출 - 스케쥴링


    //TODO 1. 독립영화 Top5 매 주 월요일 저장 - 스케쥴링
}

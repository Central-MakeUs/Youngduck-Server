package com.example.api.Popcorn.controller;

import com.example.api.Popcorn.dto.response.PopcornResponse;
import com.example.api.Popcorn.service.GetPopcornUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/popcorn")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "access-token")
public class PopcornController {
    private final GetPopcornUseCase getPopcornUseCase;
    //TODO 지난주 (테스트는 1분) 팝콘작 중 투표수 가장 높았던 것 3개 반환
    @GetMapping
    public List<PopcornResponse> getPopcorn() {
        return getPopcornUseCase.execute();
    }

    //TODO 5. 팝콘작 리뷰하기

    //TODO 6. 팝콘작 팝콘지수 산출

    //TODO 7: 팝콘작 상세 페이지


}

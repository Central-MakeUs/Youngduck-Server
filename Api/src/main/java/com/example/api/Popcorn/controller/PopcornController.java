package com.example.api.Popcorn.controller;

import com.example.api.Popcorn.dto.request.PostPopcornReviewRequest;
import com.example.api.Popcorn.dto.response.PopcornResponse;
import com.example.api.Popcorn.dto.response.PopcornReviewResponse;
import com.example.api.Popcorn.service.GetPopcornUseCase;
import com.example.api.Popcorn.service.PostPopcornReviewUseCase;
import com.example.api.screening.dto.request.PostReviewRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/popcorn")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "access-token")
public class PopcornController {
    private final GetPopcornUseCase getPopcornUseCase;
    private final PostPopcornReviewUseCase postPopcornReviewUseCase;
    @Operation(summary = "지난주 투표수 가장 높았던 3개 반환. 이번 주 상영작임", description = "이번 주 상영작 3개 가져오기")
    @GetMapping
    public List<PopcornResponse> getPopcorn() {
        return getPopcornUseCase.execute();
    }

    //TODO 5. 팝콘작 리뷰하기, 중복 금지
    @Operation(summary = "특정 팝콘작에에 리뷰 달기", description = "popcorn id가져와서 리뷰하기")
    @PostMapping("/review/{popcornId}")
    public PopcornReviewResponse reviewOnScreening(@PathVariable("popcornId")Long popcornId, @RequestBody PostPopcornReviewRequest request)
    {
        return postPopcornReviewUseCase.execute(popcornId,request);
    }

    //TODO 6. 팝콘작 팝콘지수 산출 로직

    //TODO 7: 팝콘작 상세 페이지 - 팝콘지수 GET

    //TODO 8: 팝콘 키워드 Top3 GET

    //TODO 팝콘작들에 대한 리뷰 반환

    //TODO 내가 쓴 팝콘작 리뷰




}

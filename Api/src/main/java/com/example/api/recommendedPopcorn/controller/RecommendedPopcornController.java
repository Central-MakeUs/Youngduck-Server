package com.example.api.recommendedPopcorn.controller;

import com.example.api.recommendedPopcorn.dto.request.RecommendedPopcornRequest;
import com.example.api.recommendedPopcorn.dto.response.RecommendedPopcornResponse;
import com.example.api.recommendedPopcorn.service.GetRecommendedPopocornUseCase;
import com.example.api.recommendedPopcorn.service.GetRecommendedRandomPopcornUseCase;
import com.example.api.recommendedPopcorn.service.PostRecommendPopcornUseCase;
import com.example.api.recommendedPopcorn.service.PostVoteRecommendedPopcorn;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import com.example.domains.recommendedPopcornUser.entity.RecommendedPopcornUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/popcorn/recommend")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "access-token")
public class RecommendedPopcornController {
    //TODO 2. 팝콘작 추천하기 - 예비팝콘 - movieId로 분별 (0)
    private final PostRecommendPopcornUseCase recommendedPopcornUseCase;
    private final GetRecommendedPopocornUseCase getRecommendedPopocornUseCase;
    private final PostVoteRecommendedPopcorn postVoteRecommendedPopcorn;
    private final GetRecommendedRandomPopcornUseCase getRecommendedRandomPopcornUseCase;

    @Operation(summary = "영화 추천하기", description = "영화 추천하기")
    @PostMapping
    public void postRecommendPopcorn(@RequestBody RecommendedPopcornRequest request) throws IOException {
        recommendedPopcornUseCase.execute(request);
    }

    @Operation(summary = "다음주 상영작 중 랜덤으로 세개 반환", description = "다음주 상영작 중 랜덤으로 세개 반환")
    @GetMapping
    public List<RecommendedPopcorn> getRandomThree() {
        return getRecommendedRandomPopcornUseCase.execute();
    }

    @Operation(summary = "모든 상영작 반환", description = "모든 상영작 반환")
    @GetMapping("/all")
    public List<RecommendedPopcorn> getRecommendPopcornList() {
        return getRecommendedPopocornUseCase.execute();
    }
    //TODO 4. 예비 팝콘작 투표하기
    @Operation(summary = " 예비 팝콘작 투표하기", description = "예비 팝콘작 투표하기")
    @PostMapping("/vote")
    public void voteByIdRecommendPopcorn(@RequestParam("recommendedPopcorn") Long recommendedPopcorn) {
        postVoteRecommendedPopcorn.execute(recommendedPopcorn);
    }

}

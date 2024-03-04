package com.example.api.Popcorn.controller;

import com.example.api.Popcorn.dto.request.PostPopcornReviewRequest;
import com.example.api.Popcorn.dto.response.*;
import com.example.api.Popcorn.service.*;
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
    private final GetPopcornReviewUseCase getPopcornReviewUseCase;

    private final GetTopRatedPopcornKeyword getTopRatedPopcornKeyword;
    private final GetPopcornDetailUseCase getPopcornDetailUseCase;
    private final PostPopcornReviewComplainUseCase postPopcornReviewComplainUseCase;
    @Operation(summary = "지난주 투표수 가장 높았던 3개 반환. 이번 주 상영작임", description = "이번 주 상영작 3개 가져오기")
    @GetMapping
    public List<PopcornResponse> getPopcorn() {
        return getPopcornUseCase.execute();
    }

    @Operation(summary = "이번 주 팝콘작 상세보기", description = "popcornId로 요청하기")
    @GetMapping("/{popcornId}")
    public PopcornDetailResponse getPopcornDetail(@PathVariable("popcornId") Long popcornId) {
        return getPopcornDetailUseCase.execute(popcornId);
    }

    @Operation(summary = "지난 10분 간 만들어진 것들 중에서, 투표수 가장 높았던 3개 반환. 이번 주 상영작임", description = "이번 주 상영작 3개 가져오기")
    @GetMapping("/test")
    public List<PopcornResponse> getPopcornTest() {
        return getPopcornUseCase.testExecute();
    }

    @Operation(summary = "특정 팝콘작에에 리뷰 달기", description = "popcorn id가져와서 리뷰하기")
    @PostMapping("/review/{popcornId}")
    public PopcornReviewResponse reviewOnScreening(@PathVariable("popcornId") Long popcornId, @RequestBody PostPopcornReviewRequest request) {
        return postPopcornReviewUseCase.execute(popcornId, request);
    }

    @Operation(summary = "특정 팝콘작들에 대한 팝콘 지수", description = "popcornId 가져와서 요청하기")
    @GetMapping("/rate/{popcornId}")
    public int getPopcornRate(@PathVariable("popcornId") Long popcornId) {
        return getPopcornReviewUseCase.getRate(popcornId);
    }

    @Operation(summary = "팝콘작들에 대한 리뷰 반환", description = "popcornId 가져와서 요청하기")
    @GetMapping("/reviews/{popcornId}")
    public List<PopcornReviewResponse> getReviewResponseList(@PathVariable("popcornId") Long popcornId) {
        return getPopcornReviewUseCase.execute(popcornId);
    }

    @Operation(summary = "팝콘작 리뷰 신고하기", description = "팝콘작 리뷰 신고하기")
    @PostMapping("/review/complain/{popcornUserId}")
    public void reviewsFromPopcorn(@PathVariable("popcornUserId") Long popcornUserId)
    {
        postPopcornReviewComplainUseCase.execute(popcornUserId);
    }

    @Operation(summary = "팝콘작들에 대한 나의 리뷰 반환", description = "popcornId 가져와서 요청하기")
    @GetMapping("/my/reviews")
    public List<PopcornMyReviewResponse> getReviewMyResponseList() {
        return getPopcornReviewUseCase.getMyReviews();
    }

    @Operation(summary = "팝콘 키워드 Top3 반환", description = "popcornId 가져와서 요청하기")
    @GetMapping("/top-keywords/{popcornId}")
    public PopcornKeywordResponse getTopPopcornKeywords(@PathVariable("popcornId") Long popcornId){
        return getTopRatedPopcornKeyword.execute(popcornId);
    }
}

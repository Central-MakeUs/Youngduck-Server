package com.example.api.screening.controller;

import com.example.api.screening.dto.request.PostScreeningRequest;
import com.example.api.screening.dto.response.BookMarkResponse;
import com.example.api.screening.dto.response.ScreeningUploadResponse;
import com.example.api.screening.service.BookMarkScreeningUseCase;
import com.example.api.screening.service.ReviewUseCase;
import com.example.api.screening.service.ScreeningUploadUseCase;
import com.example.api.screening.service.GetScreeningUseCase;
import com.example.domains.screening.entity.Screening;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/screening")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "access-token")
public class ScreeningController {
    private final ScreeningUploadUseCase screeningUploadUseCase;
    private final GetScreeningUseCase getScreeningUseCase;
    private final ReviewUseCase reviewUseCase;
    private final BookMarkScreeningUseCase bookMarkScreeningUseCase;

    @Operation(
            summary = "스크리닝 업로드하기",
            description = "screening RequestBody가지고 업로드")
    @PostMapping("/upload-screening")
    public Screening uploadScreening(@RequestBody PostScreeningRequest request){
        return screeningUploadUseCase.execute(request);
    }

    @Operation(summary = "스크리닝 id별로 가져오기", description = "screening id가져와서 요청하기")
    @PostMapping("/{screeningId}")
    public Screening getScreening(@PathVariable("screeningId") Long screeningId) {
        return getScreeningUseCase.execute(screeningId);
    }

    @Operation(summary = "스크리닝 id별로 찜하기", description = "screening id가져와서 찜하기")
    @PostMapping("/bookMark/{screeningId}")
    public BookMarkResponse bookMarkScreening(@PathVariable("screeningId") Long screeningId) {
        return bookMarkScreeningUseCase.execute(screeningId);
    }

    //TODO 신청하기 버튼도 bookmakr api랑 똑같은 거 쓰기

    @Operation(summary = "특정 스크리닝에 리뷰 달기", description = "screening id가져와서 리뷰하기")
    @PostMapping("/review/{screeningId}")
    public void reviewOnScreening(@PathVariable("screeningId") Long screeningId)
    {
        reviewUseCase.execute(screeningId);
    }

    //TODO 리뷰 반환하기

    //TODO 내가 남긴 리뷰 모아보기 리스트로

    //TODO 검색하기 기능 구현하기
}

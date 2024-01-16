package com.example.api.screening.controller;

import com.example.api.config.response.SuccessResponse;
import com.example.api.screening.dto.request.PostReviewRequest;
import com.example.api.screening.dto.request.PostScreeningRequest;
import com.example.api.screening.dto.response.BookMarkResponse;
import com.example.api.screening.dto.response.ScreeningUploadResponse;
import com.example.api.screening.service.*;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screeningReview.entity.ScreeningReview;
import com.example.domains.screeningReview.entity.dto.ReviewResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    private final GetScreeningListUseCase getScreeningListUseCase;
    private final GetScreeningReviewListUseCase getScreeningReviewListUseCase;

    @Operation(description = "모임 대표 이미지")
    @PostMapping(value = "/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public SuccessResponse<Object> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            String imageUrl = screeningUploadUseCase.uploadImage(file);
            SuccessResponse<Object> successResponse = SuccessResponse.onSuccess(200,imageUrl);
            return successResponse;
        } catch (IOException e) {
            throw  new IllegalArgumentException("오류");
        }
    }

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

    @Operation(summary = "본인이 만든 스크리닝 목록 가져오기", description = "screening list가져와서 요청하기")
    @PostMapping("/all")
    public List<Screening> getScreeningList() {
        return getScreeningListUseCase.execute();
    }


    @Operation(summary = "스크리닝 id별로 찜하기", description = "screening id가져와서 찜하기")
    @PostMapping("/bookMark/{screeningId}")
    public BookMarkResponse bookMarkScreening(@PathVariable("screeningId") Long screeningId) {
        return bookMarkScreeningUseCase.execute(screeningId);
    }

    //TODO 신청하기 버튼도 bookmakr api랑 똑같은 거 쓰기

    @Operation(summary = "특정 스크리닝에 리뷰 달기", description = "screening id가져와서 리뷰하기")
    @PostMapping("/review/{screeningId}")
    public void reviewOnScreening(@PathVariable("screeningId")Long screeningId, @RequestBody PostReviewRequest request)
    {
        reviewUseCase.execute(screeningId,request);
    }

    //TODO 리뷰 아이디별로 반환하기

    //TODO 내가 남긴 리뷰 모아보기 리스트로
    @Operation(summary = "본인이 리뷰남긴 리뷰 목록 가져오기", description = "screeningReview list가져와서 요청하기")
    @PostMapping("/review/all")
    public List<ReviewResponseDto> getScreeningReviewList() {
        return getScreeningReviewListUseCase.execute();
    }


    //TODO 검색하기 기능 구현하기

    //TODO 특정 스크리닝 리뷰 좋아요 누르기

}

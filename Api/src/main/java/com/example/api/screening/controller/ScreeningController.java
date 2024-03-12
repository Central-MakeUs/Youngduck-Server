package com.example.api.screening.controller;

import com.example.api.config.response.SuccessResponse;
import com.example.api.config.security.SecurityUtil;
import com.example.api.screening.dto.request.PostReviewRequest;
import com.example.api.screening.dto.request.PostScreeningRequest;
import com.example.api.screening.dto.request.ReviewNotificationRequest;
import com.example.api.screening.dto.response.*;
import com.example.api.screening.service.*;
import com.example.domains.common.util.SliceResponse;
import com.example.domains.common.util.SliceUtil;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.entity.dto.ScreeningResponseDto;
import com.example.domains.screening.enums.Category;
import com.example.domains.screeningReview.adaptor.ReviewAdaptor;
import com.example.domains.screeningReview.entity.dto.ScreeningWithReviewDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/screening")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "access-token")
public class ScreeningController {
    private final GetRateCountUseCase getRateCountUseCase;
    private final ScreeningUploadUseCase screeningUploadUseCase;
    private final GetScreeningUseCase getScreeningUseCase;
    private final PostReviewUseCase postReviewUseCase;
    private final BookMarkScreeningUseCase bookMarkScreeningUseCase;
    private final GetScreeningListUseCase getScreeningListUseCase;
    private final GetScreeningReviewListUseCase getScreeningReviewListUseCase;
    private final GetReviewUseCase getReviewUseCase;
    private final GetReviewListUseCase getReviewListUseCase;
    private final PostScreeningPrivateUseCase postScreeningPrivateUseCase;
    private final PatchScreeningUseCase patchScreeningUseCase;
    private final ScreeningAdaptor screeningAdaptor;
    private final ReviewAdaptor reviewAdaptor;
    private final GetBookMarkedScreeningsUseCase getBookMarkedScreeningUseCase;
    private final GetPastScreeningListUseCase getPastScreeningListUseCase;
    private final GetScreeningStatisticsUseCase getScreeningStatisticsUseCase;
    private final GetMyScreeningUseCase getMyScreeningUseCase;
    private final GetMostReviewedUseCase getMostReviewedUseCase;
    private final GetThisWeekScreeningsUseCase getThisWeekScreeningsUseCase;
    private final GetMostRecentScreeningUseCase getMostRecentScreeningUseCase;
    private final GetBookmarkedUpcomingScreeningsUseCase getBookmarkedUpcomingScreeningsUseCase;
    private final PostReviewComplainUseCase postReviewComplainUseCase;
    private final PostNotificationUseCase postNotificationUseCase;

    @Operation(description = "모임 대표 이미지")
    @GetMapping(value = "/image/{fileName}")
    public SuccessResponse<Object> uploadImage(@PathVariable("fileName") String fileName) throws IOException {
        try {
            String imageUrl = screeningUploadUseCase.uploadImage("images",fileName);
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
    @GetMapping("/{screeningId}")
    public ScreeningInfoResponse getScreening(@PathVariable("screeningId") Long screeningId) {
        return getScreeningUseCase.execute(screeningId);
    }

    @Operation(summary = "스크리닝 리뷰 id별로 가져오기", description = "screening review id가져와서 요청하기")
    @GetMapping("/review/{reviewId}")
    public PostReviewResponse getReviewById(@PathVariable("reviewId") Long reviewId) {
        return getReviewUseCase.execute(reviewId);
    }

    @Operation(summary = "본인이 만든 스크리닝 목록 가져오기", description = "screening list가져와서 요청하기")
    @GetMapping("/all")
    public List<Screening> getScreeningList() {
        return getScreeningListUseCase.execute();
    }


    @Operation(summary = "스크리닝 id별로 찜하기", description = "screening id가져와서 찜하기")
    @PostMapping("/bookMark/{screeningId}")
    public BookMarkResponse bookMarkScreening(@PathVariable("screeningId") Long screeningId) {
        return bookMarkScreeningUseCase.execute(screeningId);
    }

    @Operation(summary = "특정 스크리닝에 리뷰 달기", description = "screening id가져와서 리뷰하기")
    @PostMapping("/review/{screeningId}")
    public void reviewOnScreening(@PathVariable("screeningId")Long screeningId, @RequestBody PostReviewRequest request)
    {
        postReviewUseCase.execute(screeningId,request);
    }

    @Operation(summary = "특정 스크리닝에 리뷰 리스트 가져오기", description = "screening id로 리뷰리스트 가져오기")
    @GetMapping("/screening-review/{screeningId}")
    public List<ScreeningReviewUserResponse> reviewsFromScreening(@PathVariable("screeningId")Long screeningId)
    {
        return getReviewListUseCase.execute(screeningId);
    }

    @Operation(summary = "본인이 리뷰남긴 리뷰 목록 가져오기", description = "screeningReview list가져와서 요청하기")
    @GetMapping("/review/all")
    public List<ScreeningWithReviewDto> getScreeningReviewList() {
        return getScreeningReviewListUseCase.execute();
    }

    @Operation(summary = "나의 스크리닝 id별로 가져오기", description = "screening id가져와서 요청하기")
    @GetMapping("/myScreening/{screeningId}")
    public ScreeningResponse getMyScreening(@PathVariable("screeningId") Long screeningId) {
        //getMyScreeningUseCase
        return getMyScreeningUseCase.execute(screeningId);
    }

    @Operation(summary = "나의 스크리닝 비공개 on/off하기", description = "screening id가져와서 요청하기")
    @PostMapping("/myScreening/private/{screeningId}")
    public void changeMyScreeningToPrivate(@PathVariable("screeningId") Long screeningId) {
        postScreeningPrivateUseCase.execute(screeningId);
    }

    @Operation(
            summary = "스크리닝 수정하기",
            description = "screening RequestBody가지고 업로드")
    @PatchMapping("/screening/{screeningId}")
    public Screening modifyScreening(@PathVariable("screeningId") Long screeningId,@RequestBody PostScreeningRequest request){
        return patchScreeningUseCase.execute(screeningId,request);
    }

    @GetMapping("/screenings/search")
    public SliceResponse<Screening> searchScreenings(
            @RequestParam(required = false,value = "title") String title,
            @RequestParam(required = false,value= "category") Category category,
            @ParameterObject @PageableDefault(size = 10) Pageable pageable
    ) {
        return screeningAdaptor.searchScreenings(title, category, pageable);
    }

    @GetMapping("/screenings/search-by-date")
    public SliceResponse<Screening> searchScreenings(
            @RequestParam(required = false,value = "title") String title,
            @RequestParam(required = false,value= "category") Category category,
            @ParameterObject @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false, value = "sortBy") String sortBy
    ) {
        if ("createdAt".equals(sortBy)) {
            return screeningAdaptor.searchScreenings(title, category, pageable);
        } else if ("startDate".equals(sortBy)) {
            return screeningAdaptor.searchByStartDate(title, category, pageable);
        } else {
            return SliceResponse.of(SliceUtil.toSlice(Collections.emptyList(), pageable)); // 기본적으로는 빈 리스트 반환
        }
    }

    @Operation(summary = "댓글 많은 수 Top3 반환", description = "좋아요 많은 수 Top3 반환")
    @GetMapping("/most-reviewed")
    public List<ScreeningResponseDto> getMostReviewed() {
        return getMostReviewedUseCase.execute();
    }

    @Operation(summary = "현재시점에서 이번주 상영작 3개 반환", description = "현재시점에서 다음주 상영작 3개 반환")
    @GetMapping("/upcoming-Screening")
    public List<ScreeningResponseDto> getTopThreeScreening() {
        return getThisWeekScreeningsUseCase.execute();
    }

    @Operation(summary = "현재시점에서 가장 치근에 올라온 3개 반환", description = "현재시점에서 가장 치근에 올라온 3개 반환")
    @GetMapping("/recent-Screening")
    public List<ScreeningResponseDto> getRecentScreening() {
        return getMostRecentScreeningUseCase.execute();
    }

    @GetMapping("/screenings/past")
    public List<ScreeningResponse> getPassedScreenings() {
        return getPastScreeningListUseCase.execute();
    }

    @GetMapping("/screenings/upcoming")
    public List<Screening> getPastScreenings() {
        return getBookmarkedUpcomingScreeningsUseCase.execute();
    }

    @PostMapping("/review/complain/{reviewId}")
    public void postReviewComplain(@RequestParam("reviewId") Long reviewId) {
        postReviewComplainUseCase.execute(reviewId);
    }

    @GetMapping("/count")
    public GetCountResponse getScreeningCount(@RequestParam("screeningId") Long screeningId){
        return getRateCountUseCase.execute(screeningId);
    }

    @GetMapping("/bookmarked")
    public List<ScreeningResponse> getBookmarkedScreenings(){

        return getBookMarkedScreeningUseCase.execute();
    }

    @GetMapping("/my/statistics/{screeningId}")
    public ScreeningStatisticsResponse getStatistics(@PathVariable("screeningId") Long screeningId) {
        return getScreeningStatisticsUseCase.execute(screeningId);
    }

    //스크리닝 리뷰 알림을 보내는 로직 구현
//    @PostMapping("/review/notifications")
//    public NotificationResponse sendReviewNotifications(@RequestBody ReviewNotificationRequest request) {
//        return postNotificationUseCase.execute(request);
//    }

}

package com.example.api.recommendedPopcorn.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendedPopcornRequest {
    @Schema(defaultValue = "1255", description = "영화 id")
    private String movieId;
    @Schema(defaultValue = "너무 재밌어요", description = "추천하는 이유")
    private String reason;
    @Schema(defaultValue = "true", description = "게시글 정책 확인 여부")
    private boolean isAgreed;

    @Builder
    public RecommendedPopcornRequest(String movieId,String reason,boolean isAgreed) {
        this.movieId=movieId;
        this.reason=reason;
        this.isAgreed=isAgreed;
    }
}

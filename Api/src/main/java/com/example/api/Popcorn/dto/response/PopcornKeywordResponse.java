package com.example.api.Popcorn.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class PopcornKeywordResponse {
    @Schema(description = "키워드 3개 반환 리스트")
    private List<Map.Entry<String, Integer>> topThreeKeywords;
    @Schema(defaultValue = "1",description = "참여한 횟수")
    private int participatedCount;
    @Schema(defaultValue = "1",description = "참여한 유저 수")
    private int participatedUserCount;

    @Builder
    private PopcornKeywordResponse(List<Map.Entry<String, Integer>> topThreeKeywords,int participatedCount,int participatedUserCount) {
        this.topThreeKeywords = topThreeKeywords;
        this.participatedCount = participatedCount;
        this.participatedUserCount = participatedUserCount;
    }

    public static PopcornKeywordResponse from(List<Map.Entry<String, Integer>> topThreeKeywords,int participatedCount,int participatedUserCount) {
        return PopcornKeywordResponse.builder()
                .topThreeKeywords(topThreeKeywords)
                .participatedCount(participatedCount)
                .participatedUserCount(participatedUserCount)
                .build();
    }
}

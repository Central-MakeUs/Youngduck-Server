package com.example.api.screening.dto.response;

import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.enums.NegativeCount;
import com.example.domains.screening.enums.PositiveCount;
import com.example.domains.screeningReview.entity.enums.Negative;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ScreeningStatisticsResponse {
    private PositiveCount positiveCount;
    private NegativeCount negativeCount;

    @Builder
    public ScreeningStatisticsResponse(PositiveCount positiveCount, NegativeCount negativeCount) {
        this.positiveCount = positiveCount;
        this.negativeCount = negativeCount;
    }

    public static ScreeningStatisticsResponse from(Screening screening){
        return ScreeningStatisticsResponse.builder()
                .positiveCount(screening.getPositiveCount())
                .negativeCount(screening.getNegativeCount())
                .build();
    }
 }

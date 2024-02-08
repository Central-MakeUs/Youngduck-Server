package com.example.api.screening.dto.response;

import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.enums.NegativeCount;
import com.example.domains.screening.enums.PositiveCount;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ScreeningStatisticsResponse {
    private PositiveCount positiveCount;
    private NegativeCount negativeCount;
    private int totalCount;

    @Builder
    public ScreeningStatisticsResponse(PositiveCount positiveCount, NegativeCount negativeCount,int totalCount) {
        this.positiveCount = positiveCount;
        this.negativeCount = negativeCount;
        this.totalCount = totalCount;
    }

    public static ScreeningStatisticsResponse from(Screening screening,int positiveCount){
        return ScreeningStatisticsResponse.builder()
                .positiveCount(screening.getPositiveCount())
                .negativeCount(screening.getNegativeCount())
                .totalCount(positiveCount)
                .build();
    }
 }

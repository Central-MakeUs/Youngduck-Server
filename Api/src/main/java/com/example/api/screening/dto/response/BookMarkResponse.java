package com.example.api.screening.dto.response;


import com.example.api.user.model.dto.GetUserInfoResponse;
import com.example.domains.user.entity.User;
import com.example.domains.userscreening.entity.UserScreening;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookMarkResponse {
    @Schema(defaultValue = "3", description = "상영회 id")
    private Long screeningId;

    @Schema(defaultValue = "true", description = "상영회 찜하기가 되어있는지")
    private boolean isBookMarked;

    @Schema(defaultValue = "true", description = "상영회 찜하기 취소가능 여부")
    private boolean canCancel;

    @Builder
    public BookMarkResponse(Long screeningId, boolean isBookMarked, boolean canCancel) {
        this.screeningId = screeningId;
        this.isBookMarked = isBookMarked;
        this.canCancel = canCancel;
    }
    public static BookMarkResponse from(UserScreening userScreening) {
        System.out.println(userScreening.isBookmarked());
        //새로 만들어졌을 경우를 위해 조건 수정해야함
        if (!userScreening.isBookmarked()){
            return BookMarkResponse.builder()
                    .screeningId(userScreening.getScreening().getId())
                    .isBookMarked(false)
                    .canCancel(false)
                    .build();
        } else {
            return BookMarkResponse.builder()
                    .screeningId(userScreening.getScreening().getId())
                    .isBookMarked(userScreening.isBookmarked())
                    .canCancel(true)
                    .build();
        }

    }
    public static BookMarkResponse firstCreated(UserScreening userScreening) {
            return BookMarkResponse.builder()
                    .screeningId(userScreening.getScreening().getId())
                    .isBookMarked(true)
                    .canCancel(true)
                    .build();
    }
}

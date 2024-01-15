package com.example.domains.userscreening.entity;

import com.example.domains.common.model.BaseTimeEntity;
import com.example.domains.screening.entity.Screening;
import com.example.domains.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserScreening{
    //isAlarmOn해보기
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isHost;
    private boolean isBookmarked;
    private boolean isParticipating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    private Screening screening;

    @Builder
    private UserScreening(boolean isHost, boolean isBookmarked, boolean isParticipating,User user, Screening screening) {
        this.isHost = isHost;
        this.isBookmarked = isBookmarked;
        this.isParticipating = isParticipating;
        this.user =user;
        this.screening = screening;
    }

    public static UserScreening of(boolean isHost, boolean isBookmarked, boolean isParticipating,User user, Screening screening) {
        return UserScreening.builder()
                .isHost(isHost)
                .isBookmarked(isBookmarked)
                .isParticipating(isParticipating)
                .user(user)
                .screening(screening)
                .build();

    }
    public void updateBookmarkedAndParticipating(boolean newIsBookmarked, boolean newIsParticipating) {
        this.isBookmarked = newIsBookmarked;
        this.isParticipating = newIsParticipating;
    }
}

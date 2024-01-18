package com.example.domains.quit.domain;

import com.example.domains.common.model.BaseTimeEntity;
import com.example.domains.quit.domain.enums.Reason;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quit extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    private Reason reason;

    @Builder
    private Quit(Long userId, Reason reason) {
        this.userId = userId;
        this.reason = reason;
    }

    public static Quit of(Long userId, Reason reason) {
        return Quit.builder().userId(userId).reason(reason).build();
    }
}
package com.example.domains.popcornReview.entity.enums;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//지워야함
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopcornPositive {
    private boolean cineMaster = false;
    private boolean greatFilming = false;
    private boolean pom = false;
    private boolean animationIsGood = false;
    private boolean artIsGood = false;
    private boolean custom = false;
    private boolean music = false;
    private boolean topicIsGood = false;
    private boolean linesAreGood = false;
    private boolean endingIsGood = false;

    private boolean castingIsGood = false;

    private boolean actingIsGood = false;

    private boolean chemistryIsGood = false;
}

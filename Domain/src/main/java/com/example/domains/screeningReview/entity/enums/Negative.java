package com.example.domains.screeningReview.entity.enums;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Negative {

    private boolean iffy = false;
    private boolean badEditing = false;
    private boolean badAngle = false;
    private boolean badDetail = false;

    private  boolean badColor = false;
    private boolean badCustom = false;
    private boolean badMusic = false;
    private boolean badSound = false;
    private boolean badEnding = false;
    private boolean endingLoose = false;

    private boolean noDetail  = false;
    private boolean badTopic = false;

    private boolean badActing = false;
    private boolean badCasting = false;

}

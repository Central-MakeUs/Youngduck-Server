package com.example.domains.screening.enums;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class NegativeCount {

    private int iffy ;
    private int badEditing ;
    private int badAngle ;
    private int badDetail;

    private int badColor ;
    private int badCustom;
    private int badMusic ;
    private int badSound;
    private int badEnding ;
    private int endingLoose ;

    private int noDetail ;
    private int badTopic ;

    private int badActing ;
    private int badCasting ;
}

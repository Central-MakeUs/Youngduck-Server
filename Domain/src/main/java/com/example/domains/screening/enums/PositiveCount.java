package com.example.domains.screening.enums;

import com.example.domains.screening.entity.Screening;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PositiveCount {

    private int cineMaster;
    private int greatFilming;
    private int pom;
    private int animationIsGood;
    private int artIsGood;
    private int custom;
    private int music;
    private int topicIsGood;
    private int linesAreGood;
    private int endingIsGood;

    private int castingIsGood;

    private int actingIsGood;

    private int chemistryIsGood;

}
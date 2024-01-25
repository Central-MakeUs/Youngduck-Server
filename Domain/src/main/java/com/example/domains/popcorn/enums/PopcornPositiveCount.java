package com.example.domains.popcorn.enums;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PopcornPositiveCount {

    private int cineMaster;
    private int greatFilming;
    private int pom;
    private int animationIsGood;
    private int artIsGood;
    private int setIsArt;
    private int custom;
    private int music;
    private int ost;
    private int writtenByGod;
    private int topicIsGood;
    private int linesAreGood;
    private int endingIsGood;

    private int castingIsGood;

    private int actingIsGood;

    private int chemistryIsGood;

}
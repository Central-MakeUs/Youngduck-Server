package com.example.domains.popcorn.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class PopcornKeywordResponseDto {
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

    @QueryProjection
    public PopcornKeywordResponseDto(
            int cineMaster, int greatFilming, int pom, int animationIsGood, int artIsGood,
            int setIsArt, int custom, int music, int ost, int writtenByGod, int topicIsGood,
            int linesAreGood, int endingIsGood, int castingIsGood, int actingIsGood,
            int chemistryIsGood, int iffy, int badEditing, int badAngle, int badDetail,
            int badColor, int badCustom, int badMusic, int badSound, int badEnding,
            int endingLoose, int noDetail, int badTopic, int badActing, int badCasting
    ) {
        this.cineMaster = cineMaster;
        this.greatFilming = greatFilming;
        this.pom = pom;
        this.animationIsGood = animationIsGood;
        this.artIsGood = artIsGood;
        this.setIsArt = setIsArt;
        this.custom = custom;
        this.music = music;
        this.ost = ost;
        this.writtenByGod = writtenByGod;
        this.topicIsGood = topicIsGood;
        this.linesAreGood = linesAreGood;
        this.endingIsGood = endingIsGood;
        this.castingIsGood = castingIsGood;
        this.actingIsGood = actingIsGood;
        this.chemistryIsGood = chemistryIsGood;

        this.iffy = iffy;
        this.badEditing = badEditing;
        this.badAngle = badAngle;
        this.badDetail = badDetail;
        this.badColor = badColor;
        this.badCustom = badCustom;
        this.badMusic = badMusic;
        this.badSound = badSound;
        this.badEnding = badEnding;
        this.endingLoose = endingLoose;
        this.noDetail = noDetail;
        this.badTopic = badTopic;
        this.badActing = badActing;
        this.badCasting = badCasting;

        // ... set other fields
    }
}

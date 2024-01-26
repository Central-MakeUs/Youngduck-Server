package com.example.api.Popcorn.service;

import com.example.adaptor.UseCase;
import com.example.domains.popcorn.adaptor.PopcornAdaptor;
import com.example.domains.popcorn.entity.dto.PopcornKeywordResponseDto;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class GetTopRatedPopcornKeyword {
    private final PopcornAdaptor popcornAdaptor;
    public  List<Map.Entry<String, Integer>> execute(Long popcornId) {
        PopcornKeywordResponseDto keywordList = popcornAdaptor.getTopRatedCounts(popcornId);

        Map<String, Integer> newList = addingToList(keywordList);
        // 내림차순으로 정렬
// Map을 리스트로 변환
        List<Map.Entry<String, Integer>> sortedList = newList.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toList());


        return sortedList;
    }

    private Map<String, Integer> addingToList(PopcornKeywordResponseDto keywordList) {
        Map<String, Integer> valueMap = new HashMap<>();
        valueMap.put("cineMaster", keywordList.getCineMaster());
        valueMap.put("greatFilming", keywordList.getGreatFilming());
        valueMap.put("pom", keywordList.getPom());
        valueMap.put("animationIsGood", keywordList.getAnimationIsGood());
        valueMap.put("artIsGood", keywordList.getArtIsGood());
        valueMap.put("setIsArt", keywordList.getSetIsArt());
        valueMap.put("custom", keywordList.getCustom());
        valueMap.put("music", keywordList.getMusic());
        valueMap.put("ost", keywordList.getOst());
        valueMap.put("writtenByGod", keywordList.getWrittenByGod());
        valueMap.put("topicIsGood", keywordList.getTopicIsGood());
        valueMap.put("linesAreGood", keywordList.getLinesAreGood());
        valueMap.put("endingIsGood", keywordList.getEndingIsGood());
        valueMap.put("castingIsGood", keywordList.getCastingIsGood());
        valueMap.put("actingIsGood", keywordList.getActingIsGood());
        valueMap.put("chemistryIsGood", keywordList.getChemistryIsGood());
        valueMap.put("iffy", keywordList.getIffy());
        valueMap.put("badEditing", keywordList.getBadEditing());
        valueMap.put("badAngle", keywordList.getBadAngle());
        valueMap.put("badDetail", keywordList.getBadDetail());
        valueMap.put("badColor", keywordList.getBadColor());
        valueMap.put("badCustom", keywordList.getBadCustom());
        valueMap.put("badMusic", keywordList.getBadMusic());
        valueMap.put("badSound", keywordList.getBadSound());
        valueMap.put("badEnding", keywordList.getBadEnding());
        valueMap.put("endingLoose", keywordList.getEndingLoose());
        valueMap.put("noDetail", keywordList.getNoDetail());
        valueMap.put("badTopic", keywordList.getBadTopic());
        valueMap.put("badActing", keywordList.getBadActing());
        valueMap.put("badCasting", keywordList.getBadCasting());

        return valueMap;
    }


    //TODO Positive먼저 나오게
}

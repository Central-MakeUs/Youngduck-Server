package com.example.api.diverseMovie.controller;

import com.example.api.diverseMovie.dto.response.DiverseMovieResponse;
import com.example.api.diverseMovie.service.GetTopRatedMovies;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/diverse")
@RequiredArgsConstructor
@Slf4j
public class DiverseMovieController {
    private final GetTopRatedMovies getTopRatedMovies;

    @GetMapping("/dev")
    public void savePopcorn(){
        getTopRatedMovies.getTopRated();
    }


    //TODO 1. 독립영화 Top5 매 주 월요일 저장 - 스케쥴링 - 완료

    @GetMapping("/toprated")
    public List<DiverseMovieResponse> getDiverseMovies() {
        return getTopRatedMovies.getTopratedList();
    }
}

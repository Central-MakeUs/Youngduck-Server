package com.example.api.Popcorn.service;

import com.example.adaptor.UseCase;
import com.example.api.Popcorn.dto.response.PopcornResponse;
import com.example.domains.popcorn.adaptor.PopcornAdaptor;
import com.example.domains.popcorn.entity.Popcorn;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;
@UseCase
@RequiredArgsConstructor
public class GetPopcornUseCase {
    private final PopcornAdaptor popcornAdaptor;
    public List<PopcornResponse> execute() {
        List<Popcorn> popcornList = popcornAdaptor.findLastWeekPopcorns();
        List<PopcornResponse> response = new ArrayList<>();

        for(Popcorn popcorn : popcornList) {
            final PopcornResponse popcornResponse = PopcornResponse.from(popcorn);
            response.add(popcornResponse);
        }
        return response;
    }

    public List<PopcornResponse> testExecute() {
        List<Popcorn> popcornList = popcornAdaptor.findPastHoursPopcorns();
        List<PopcornResponse> response = new ArrayList<>();

        for(Popcorn popcorn : popcornList) {
            final PopcornResponse popcornResponse = PopcornResponse.from(popcorn);
            response.add(popcornResponse);
        }
        return response;
    }
}

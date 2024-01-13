package com.example.api.screening.controller;

import com.example.api.screening.dto.request.PostScreeningRequest;
import com.example.api.screening.service.ScreeningUploadUseCase;
import com.example.domains.screening.entity.Screening;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/screening")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "access-token")
public class ScreeningController {
    private final ScreeningUploadUseCase screeningUploadUseCase;

    @Operation(
            summary = "스크리닝 업로드하기",
            description = "screening RequestBody가지고 업로드")
    @PostMapping("/upload-screening")
    public Screening uploadScreening(@RequestBody PostScreeningRequest request){
        return screeningUploadUseCase.execute(request);
    }
}

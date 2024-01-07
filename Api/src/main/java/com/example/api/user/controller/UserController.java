package com.example.api.user.controller;

import com.example.api.user.model.dto.GetUserInfoResponse;
import com.example.api.user.service.GetUserInfoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "access-token")
public class UserController {
    private final GetUserInfoUseCase getUserInfoUseCase;

    @Operation(summary = "내 정보를 가져옵니다.")
    @GetMapping(value = "/info")
    public GetUserInfoResponse getUserInfo() {
        return getUserInfoUseCase.execute();
    }

}

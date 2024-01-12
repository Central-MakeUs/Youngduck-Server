package com.example.api.auth.controller;

import com.example.api.auth.model.dto.response.OauthRegisterResponse;
import com.example.api.auth.service.LogOutUserUseCase;
import com.example.api.auth.service.TokenRefreshUseCase;
import com.example.api.auth.service.helper.WithdrawUseCase;
import com.example.api.config.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.awt.desktop.UserSessionEvent;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final WithdrawUseCase withdrawUserUseCase;
    private final LogOutUserUseCase logOutUserUseCase;
    private final TokenRefreshUseCase tokenRefreshUseCase;

    @Operation(summary = "회원탈퇴")
    @DeleteMapping("/withdraw")
    public void withDrawUser(
            @RequestParam(required = false, name = "appleCode", value = "") String appleCode,
            @RequestHeader(value = "referer", required = false) String referer,
            @RequestParam("quitReason") UserSessionEvent.Reason reason) {
        Long userId = SecurityUtil.getCurrentUserId();
        withdrawUserUseCase.execute(appleCode, referer, userId, reason);
    }

    @Operation(summary = "회원탈퇴를 합니다. (개발용)", deprecated = true)
    @DeleteMapping("/withdrawal/dev")
    public void withDrawUserDev(
            @RequestParam(required = false, name = "appleCode", value = "") String appleCode) {
        withdrawUserUseCase.executeDev(appleCode);
    }

    @Operation(summary = "로그아웃을 합니다.")
    @PostMapping("/logout")
    public void logOutUser() {
        logOutUserUseCase.execute();
    }

    @Operation(summary = "토큰 재발급을 합니다.")
    @PostMapping("/token/refresh")
    public OauthRegisterResponse refreshToken(
            @RequestParam(value = "refreshToken") String refreshToken) {
        return tokenRefreshUseCase.execute(refreshToken);
    }
}

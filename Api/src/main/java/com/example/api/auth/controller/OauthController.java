package com.example.api.auth.controller;

import com.example.api.auth.model.dto.request.RegisterRequest;
import com.example.api.auth.model.dto.response.OauthLoginLinkResponse;
import com.example.api.auth.model.dto.response.OauthRegisterResponse;
import com.example.api.auth.model.dto.response.OauthSignInResponse;
import com.example.api.auth.service.OauthLinkUseCase;
import com.example.api.auth.service.OauthLoginUseCase;
import com.example.api.auth.service.OauthRegisterUseCase;
import com.example.domains.user.enums.OauthProvider;
import com.example.dto.IdTokenDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/oauth")
@RequiredArgsConstructor
@Slf4j
public class OauthController {
    private final OauthLinkUseCase oauthLinkUseCase;
    private final OauthLoginUseCase oauthLoginUseCase;
    private final OauthRegisterUseCase oauthRegisterUseCase;

    @Operation(
            summary = "oauth 링크발급",
            description = "oauth 링크를 받는 api",
            deprecated = true)
    @GetMapping("/link/{provider}")
    public OauthLoginLinkResponse getOauthLink(
            @PathVariable("provider") OauthProvider provider,
            @RequestHeader(value = "referer", required = false) String referer) {
        return oauthLinkUseCase.getOauthLink(provider, referer);
    }


    @Operation(
            summary = "code발급 받기",
            description = "referer 입력 필요 없습니다. 회원가입 안된 유저일 경우, canLogin=false 값을 반환하고 idTOken만 가져다가 register부분에 넣어서 요청해주시면 됩니다")
    @PostMapping("/login/{provider}/code")
    public OauthSignInResponse oauthUserCodeLogin(
            @PathVariable("provider") OauthProvider provider,
            @RequestParam("code") String code,
            @RequestHeader(value = "referer", required = false) String referer
            ){
        return oauthLoginUseCase.loginWithCode(provider, code, referer);
    }

    @Operation(summary = "로그인 (idtoken 용)", description = "회원가입 안된 유저일 경우, canLogin=false 값을 보냅니다!")
    @PostMapping("/login/{provider}/idtoken")
    public OauthSignInResponse oauthUserIdTokenLogin(
            @PathVariable("provider") OauthProvider provider,
            @RequestParam("idToken") String idToken) {
        return oauthLoginUseCase.loginWithIdToken(provider, idToken);
    }

    @Operation(summary = "회원가입")
    @PostMapping("/register/{provider}")
    public OauthRegisterResponse oauthUserRegister(
            @PathVariable("provider") OauthProvider provider,
            @RequestParam("idToken") String idToken,
            @RequestBody RegisterRequest registerRequest) {
        return oauthRegisterUseCase.execute(provider, idToken, registerRequest);
    }


}
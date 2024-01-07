package com.example.api.auth.service;

import com.example.adaptor.UseCase;
import com.example.api.auth.model.dto.response.OauthRegisterResponse;
import com.example.api.auth.service.helper.TokenGenerateHelper;
import com.example.domains.user.adaptor.RefreshTokenAdaptor;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.entity.RefreshTokenEntity;
import com.example.domains.user.entity.User;
import com.example.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class TokenRefreshUseCase {
    private final RefreshTokenAdaptor refreshTokenAdaptor;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserAdaptor userAdaptor;
    private final TokenGenerateHelper tokenGenerateHelper;

    @Transactional
    public OauthRegisterResponse execute(String refreshToken) {
        RefreshTokenEntity oldToken = refreshTokenAdaptor.findTokenByRefreshToken(refreshToken);
        Long userId = jwtTokenProvider.parseRefreshToken(oldToken.getRefreshToken());
        User user = userAdaptor.findById(userId);
        refreshTokenAdaptor.delete(oldToken);
        return OauthRegisterResponse.from(tokenGenerateHelper.execute(user));
    }
}

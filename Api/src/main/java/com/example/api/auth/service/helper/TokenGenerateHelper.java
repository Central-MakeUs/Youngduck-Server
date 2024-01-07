package com.example.api.auth.service.helper;

import com.example.adaptor.Helper;
import com.example.api.auth.model.dto.response.OauthSignInResponse;
import com.example.domains.user.adaptor.RefreshTokenAdaptor;
import com.example.domains.user.entity.RefreshTokenEntity;
import com.example.domains.user.entity.User;
import com.example.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Helper
@RequiredArgsConstructor
public class TokenGenerateHelper {

    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenAdaptor refreshTokenAdaptor;

    @Transactional
    public OauthSignInResponse execute(User user) {
        String newAccessToken =
                jwtTokenProvider.generateAccessToken(user.getId(), user.getUserRole().getValue());
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

        RefreshTokenEntity newRefreshTokenEntity =
                RefreshTokenEntity.builder()
                        .refreshToken(newRefreshToken)
                        .id(user.getId())
                        .ttl(jwtTokenProvider.getRefreshTokenTTLSecond())
                        .build();
        refreshTokenAdaptor.save(newRefreshTokenEntity);

        return OauthSignInResponse.of(
                true,
                null,
                newAccessToken,
                jwtTokenProvider.getAccessTokenTTLSecond(),
                newRefreshToken,
                jwtTokenProvider.getRefreshTokenTTLSecond());
    }
}
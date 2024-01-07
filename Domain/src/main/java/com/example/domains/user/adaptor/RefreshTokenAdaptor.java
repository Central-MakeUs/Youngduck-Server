package com.example.domains.user.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.user.entity.RefreshTokenEntity;
import com.example.domains.user.repository.RefreshTokenRepository;
import com.example.error.exception.ExpiredRefreshTokenException;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class RefreshTokenAdaptor {
    private final RefreshTokenRepository refreshTokenRepository;

    public void save(RefreshTokenEntity newRefreshTokenEntityEntity) {
        refreshTokenRepository.save(newRefreshTokenEntityEntity);
    }

    public void deleteTokenByUserId(Long userId) {
        refreshTokenRepository.deleteById(userId.toString());
    }

    public RefreshTokenEntity findTokenByRefreshToken(String refreshToken) {
        return refreshTokenRepository
                .findByRefreshToken(refreshToken)
                .orElseThrow(() -> ExpiredRefreshTokenException.EXCEPTION);
    }

    public void delete(RefreshTokenEntity token) {
        refreshTokenRepository.delete(token);
    }
}

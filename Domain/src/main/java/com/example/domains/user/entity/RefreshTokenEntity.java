package com.example.domains.user.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "refreshToken")
@Getter
public class RefreshTokenEntity {
    @Id
    private Long id;
    @Indexed
    private String refreshToken;
    @TimeToLive // TTL
    private Long ttl;

    @Builder
    private RefreshTokenEntity(Long id, String refreshToken, Long ttl) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.ttl = ttl;
    }

    public static RefreshTokenEntity of(Long id, String refreshToken, Long ttl) {
        return RefreshTokenEntity.builder().id(id).refreshToken(refreshToken).ttl(ttl).build();
    }
}
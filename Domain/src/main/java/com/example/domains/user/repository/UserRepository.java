package com.example.domains.user.repository;

import com.example.domains.user.entity.User;
import com.example.domains.user.enums.OauthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOauthInfo(OauthInfo oauthInfo);

    boolean existsByOauthInfo(OauthInfo oauthInfo);

    boolean existsByNickname(String nickname);

}
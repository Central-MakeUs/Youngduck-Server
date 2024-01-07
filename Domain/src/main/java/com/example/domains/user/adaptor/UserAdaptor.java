package com.example.domains.user.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.user.entity.User;
import com.example.domains.user.enums.OauthInfo;
import com.example.domains.user.exception.exceptions.UserNotFoundException;
import com.example.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class UserAdaptor {
    private final UserRepository userRepository;

    public Boolean exist(OauthInfo oauthInfo) {
        return userRepository.existsByOauthInfo(oauthInfo);
    }

    public User findByOauthInfo(OauthInfo oauthInfo) {
        return userRepository
                .findByOauthInfo(oauthInfo)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    public Boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public Boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }
}
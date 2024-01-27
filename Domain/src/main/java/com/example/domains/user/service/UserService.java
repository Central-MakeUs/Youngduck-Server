package com.example.domains.user.service;

import com.example.adaptor.DomainService;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.entity.User;
import com.example.domains.user.enums.Genre;
import com.example.domains.user.enums.OauthInfo;
import com.example.domains.user.exception.exceptions.UserNotFoundException;
import com.example.domains.user.repository.UserRepository;
import com.example.domains.user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Level;

@DomainService
@RequiredArgsConstructor
public class UserService {
    private final UserAdaptor userAdaptor;
    private final UserValidator userValidator;
    private final UserRepository userRepository;


    @Transactional
    public User registerUser(
            String nickname,
            boolean lawAgreement,
            List<Genre> genres,
            String email,
            String name,
            OauthInfo oauthInfo,
            int profileImgNumber) {
        userValidator.validUserCanRegister(oauthInfo.getOid());
        final User newUser =
                User.of(
                        nickname,
                        lawAgreement,
                        genres,
                        email,
                        name,
                        oauthInfo,
                        profileImgNumber);
        userAdaptor.save(newUser);
        return newUser;
    }

    @Transactional
    public User loginUser(OauthInfo oauthInfo) {
        User user = userAdaptor.findByOauthInfo(oauthInfo.getOid());
        user.login();
        return user;
    }
    public Boolean checkUserCanLogin(String oid) {
        return userAdaptor.exist(oid);
    }

    @Transactional
    public void deleteUserById(Long userId) {
        User user = userAdaptor.findById(userId);
        user.withdrawUser();
    }


    public void updateUserById(Long userId,String name) {
        User user = userAdaptor.findById(userId);
        user.updateInfo(name);
    }


    public List<Genre> getUserGenres(Long userId) {
        // Retrieve the user from the repository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // Return the user's genres
        return user.getGenres();
    }
}

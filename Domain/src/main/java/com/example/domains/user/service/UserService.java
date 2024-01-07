package com.example.domains.user.service;

import com.example.adaptor.DomainService;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.entity.User;
import com.example.domains.user.enums.Genre;
import com.example.domains.user.enums.OauthInfo;
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


    @Transactional
    public User registerUser(
            String nickname,
            List<Genre> genres,
            boolean lawAgreement,
            OauthInfo oauthInfo) {
        userValidator.validUserCanRegister(oauthInfo);
        final User newUser =
                User.of(
                        nickname,
                        genres,
                        lawAgreement,
                        oauthInfo);
        userAdaptor.save(newUser);
        return newUser;
    }

    @Transactional
    public User loginUser(OauthInfo oauthInfo) {
        User user = userAdaptor.findByOauthInfo(oauthInfo);
        user.login();
        return user;
    }
    public Boolean checkUserCanLogin(OauthInfo oauthInfo) {
        return userAdaptor.exist(oauthInfo);
    }


}

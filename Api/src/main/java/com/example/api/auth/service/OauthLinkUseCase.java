package com.example.api.auth.service;

import com.example.adaptor.UseCase;
import com.example.api.auth.model.dto.response.OauthLoginLinkResponse;
import com.example.api.auth.service.helper.OauthHelper;
import com.example.domains.user.enums.OauthProvider;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class OauthLinkUseCase {
    private final OauthHelper oauthHelper;

    public OauthLoginLinkResponse getOauthLinkDev(OauthProvider provider) {
        return oauthHelper.getOauthLinkDev(provider);
    }

    public OauthLoginLinkResponse getOauthLink(OauthProvider provider, String referer) {
        return oauthHelper.getOauthLink(provider, referer);
    }
}
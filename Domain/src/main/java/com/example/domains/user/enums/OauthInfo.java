package com.example.domains.user.enums;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthInfo {
    @Enumerated(EnumType.STRING)
    private OauthProvider provider;

    private String oid;
    private String email;

    @Builder
    private OauthInfo(OauthProvider provider, String oid,String email) {
        this.provider = provider;
        this.oid = oid;
        this.email =email;
    }

    public static OauthInfo of(OauthProvider provider, String oid,String email) {
        return OauthInfo.builder().provider(provider).oid(oid).email(email).build();
    }

    public void withDrawOauthInfo() {
        this.oid = null;
    }
}

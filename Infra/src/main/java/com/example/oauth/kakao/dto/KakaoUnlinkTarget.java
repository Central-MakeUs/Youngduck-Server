package com.example.oauth.kakao.dto;

import lombok.Getter;

@Getter
public class KakaoUnlinkTarget {

    @feign.form.FormProperty("target_id_type")
    private String targetIdType = "user_id";

    @feign.form.FormProperty("target_id")
    private String aud;

    private KakaoUnlinkTarget(String aud) {
        this.aud = aud;
    }

    public static KakaoUnlinkTarget from(String aud) {
        return new KakaoUnlinkTarget(aud);
    }
}
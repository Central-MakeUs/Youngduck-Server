package com.example.domains.screening.enums;

import com.example.domains.common.model.BaseTimeEntity;
import com.example.domains.user.enums.OauthInfo;
import com.example.domains.user.enums.OauthProvider;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HostInfo  {
    private String hostName;
    private String hostPhoneNumber;
    private String hostEmail;
    @Builder
    private HostInfo(String hostName, String hostPhoneNumber, String hostEmail) {
            this.hostName=hostName;
            this.hostPhoneNumber=hostPhoneNumber;
            this.hostEmail=hostEmail;
    }

    public static HostInfo of(String hostName, String hostPhoneNumber, String hostEmail) {
        return HostInfo.builder().hostName(hostName).hostPhoneNumber(hostPhoneNumber).hostEmail(hostEmail).build();
    }
}

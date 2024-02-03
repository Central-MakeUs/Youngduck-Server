package com.example.api.auth.model.dto.request;

import com.example.adaptor.ValidEnum;
import com.example.domains.user.enums.Genre;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    @Schema(defaultValue = "닉네임", description = "닉네임")
    @NotBlank(message = "닉네임을 입력하세요")
    private String nickname;

    @Schema(defaultValue = "false", description = "약관정책 동의 여부")
    private boolean lawAgreement;

    @Schema(defaultValue = "false", description = "마케팅 정보 수신 동의 여부")
    private boolean marketingAgreement;

    @ArraySchema(schema = @Schema(description = "좋아하는 영화 장르"))
    private List<@ValidEnum(target = Genre.class) Genre> genres;

    @Schema(defaultValue = "이름", description = "이름, 애플 회원가입용")
    private String name;
//
    @Schema(defaultValue = "이메일", description = "이메일, 애플 회원가입용")
    private String email;
}

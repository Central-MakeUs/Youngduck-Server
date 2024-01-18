package com.example.api.user.controller;

import com.example.api.config.security.SecurityUtil;
import com.example.api.user.model.dto.GetUserInfoResponse;
import com.example.api.user.service.GetUserInfoUseCase;
import com.example.domains.user.enums.Genre;
import com.example.domains.user.exception.exceptions.UserNotFoundException;
import com.example.domains.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "access-token")
public class UserController {
    private final GetUserInfoUseCase getUserInfoUseCase;
    private final UserService userService;

    @Operation(summary = "내 정보를 가져옵니다.")
    @GetMapping(value = "/info")
    public GetUserInfoResponse getUserInfo() {
        return getUserInfoUseCase.execute();
    }

    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getUserGenres() {
        try {
            Long userId = SecurityUtil.getCurrentUserId();
            // Call the UserService to retrieve user genres by userId
            List<Genre> genres = userService.getUserGenres(userId);
            return ResponseEntity.ok(genres);
        } catch (UserNotFoundException e) {
            // Handle the case where the user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

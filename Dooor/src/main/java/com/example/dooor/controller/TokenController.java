package com.example.dooor.controller;

import com.example.dooor.dto.User.AccessTokenRes;
import com.example.dooor.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final TokenProvider tokenProvider;

    @PostMapping("/api/token")
    public ResponseEntity<AccessTokenRes> createNewAccessToken(HttpServletRequest request) {
        return ResponseEntity.ok(tokenProvider.reissueAccessToken(request));
    }
}

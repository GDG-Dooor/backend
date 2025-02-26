package com.example.dooor.jwt.api;

import com.example.dooor.jwt.application.TokenProvider;
import com.example.dooor.jwt.api.dto.response.AccessTokenRes;
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
    @PostMapping("/api/token/revoke")
    public ResponseEntity<Void> revokeTokens(HttpServletRequest request) {
        // 엑세스 토큰 무효화 로직 추가
        String accessToken = tokenProvider.resolveToken(request); // 요청에서 엑세스 토큰 추출
        tokenProvider.revokeAccessToken(accessToken); // 블랙리스트에 추가하는 메서드 호출

        return ResponseEntity.ok().build(); // 성공적으로 무효화
    }
}

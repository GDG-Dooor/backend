package com.example.dooor.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
//로그인 후 응답 정보를 포함하며, 사용자 ID와 인증 토큰 등을 전송
public class LoginResponseDTO {

    private Integer userId; // 사용자 ID
    private String token; // 인증 토큰
    private String message; // 로그인 메시지
}

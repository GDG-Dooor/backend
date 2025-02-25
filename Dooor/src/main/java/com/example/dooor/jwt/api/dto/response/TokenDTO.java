package com.example.dooor.jwt.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
}

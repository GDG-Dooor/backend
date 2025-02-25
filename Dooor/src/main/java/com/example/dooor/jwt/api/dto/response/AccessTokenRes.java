package com.example.dooor.jwt.api.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccessTokenRes {
    private String accessToken;
}

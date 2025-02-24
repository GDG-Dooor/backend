package com.example.dooor.dto.User;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccessTokenRes {
    private String accessToken;
}

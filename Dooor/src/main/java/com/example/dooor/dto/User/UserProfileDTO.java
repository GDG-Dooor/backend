package com.example.dooor.dto.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileDTO {

    private String name; // 사용자 이름
    private String email; // 사용자 이메일
//    private String gender; // 사용자 성별
    private Integer rank; // 사용자 랭킹 점수
}

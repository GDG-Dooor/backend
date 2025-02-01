package com.example.dooor.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileDTO {

    private Integer userId; // 사용자 ID
    private String name; // 사용자 이름
    private String email; // 사용자 이메일
    private Integer rank; // 사용자 랭킹 점수
}

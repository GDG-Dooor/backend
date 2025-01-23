package com.example.dooor.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//로그아웃 정보 전송 DTO
public class LogoutDTO {

    private Integer userId; // 사용자 ID
}

package com.example.dooor.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
//비밀번호 재설정 요청을 위한 DTO로, 사용자 이메일과 새 비밀번호를 포함
public class PasswordResetDTO {
// 인증을 어떻게 받을지에 대한 로직 회의 필요
    @NotBlank
    @Email
    private String email; // 사용자 이메일

    @NotBlank
    private String newPassword; // 새 비밀번호
}

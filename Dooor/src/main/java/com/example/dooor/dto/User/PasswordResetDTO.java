package com.example.dooor.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//비밀번호 재설정 요청을 위한 DTO로, 사용자 이메일과 새 비밀번호를 포함
public class PasswordResetDTO {

    @NotBlank
    @Email
    private String email; // 사용자 이메일

    @NotBlank
    private String newPassword; // 새 비밀번호
}

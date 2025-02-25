package com.example.dooor.user.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignUpDTO {

    @NotBlank
    @Size(max = 50)
    @Schema(description = "사용자 이름", example = "홍길동")
    private String name; // 이름

    @NotBlank
    @Email(message = "이메일 양식에 맞지 않습니다.")
    @Size(max = 100)
    @Schema(description = "사용자 이메일", example = "example@gmail.com")
    private String email; // 이메일 주소

    @NotBlank
    @Size(min = 8, max = 12)
    @Schema(description = "계정 암호", example = "abcd1234")
    private String password; // 비밀번호

    @NotBlank
    private String passwordConfirm; // 비밀번호 확인
//
//    @NotBlank
//    private String gender; // 성별(남성 or 여성)

    // private String googleId; // 구글 계정 ID (구글 로그인 시 사용)

    // private String provider; // 인증 제공자 (예: "google")
}

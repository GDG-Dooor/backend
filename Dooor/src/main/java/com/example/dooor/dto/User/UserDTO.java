package com.example.dooor.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotBlank
    @Size(max = 50)
    private String nickname; // 닉네임

    @NotBlank
    @Email
    @Size(max = 100)
    private String email; // 이메일 주소

    @NotBlank
    @Size(min = 8, max = 255)
    private String password; // 비밀번호

    private String phoneNumber; // 전화번호

    private String googleId; // 구글 계정 ID (구글 로그인 시 사용)

    private String provider; // 인증 제공자 (예: "google")

    // 기본 생성자
    public UserDTO() {}

    // Getter 및 Setter
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}

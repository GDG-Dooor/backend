package com.example.dooor.dto.User;

//로그인 후 응답 정보를 포함하며, 사용자 ID와 인증 토큰 등을 전송
public class LoginResponseDTO {

    private Integer userId; // 사용자 ID
    private String token; // 인증 토큰
    private String message; // 로그인 메시지

    // 기본 생성자
    public LoginResponseDTO() {}

    // Getter 및 Setter
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

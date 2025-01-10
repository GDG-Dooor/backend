package com.example.dooor.dto.User;

//로그아웃 정보 전송 DTO
public class LogoutDTO {

    private Integer userId; // 사용자 ID

    // 기본 생성자
    public LogoutDTO() {}

    // Getter 및 Setter
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

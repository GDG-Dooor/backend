package com.example.dooor.dto;

import jakarta.validation.constraints.NotBlank;
//사용자 ID와 피드백 메시지를 포함합니다. 클라이언트에서 서버로 피드백을 전송할 때 사용
public class FeedbackDTO {

    private Integer userId; // 사용자 ID

    @NotBlank
    private String message; // 피드백 메시지

    // 기본 생성자
    public FeedbackDTO() {}

    // Getter 및 Setter
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

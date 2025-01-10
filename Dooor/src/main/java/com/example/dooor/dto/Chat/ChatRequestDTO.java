package com.example.dooor.dto.Chat;

//사용자가 채팅 메시지를 전송할 때 필요한 정보를 포함합니다. 사용자 ID와 메시지 내용만 필요
public class ChatRequestDTO {

    private Integer userId; // 사용자 ID
    private String message; // 메시지 내용

    // 기본 생성자
    public ChatRequestDTO() {}

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

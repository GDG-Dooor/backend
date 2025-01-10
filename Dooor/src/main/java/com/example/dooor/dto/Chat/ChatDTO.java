package com.example.dooor.dto.Chat;

//데이터베이스에서 채팅 메시지를 조회할 때 사용되며, 채팅 메시지의 ID, 사용자 ID, 메시지 내용, 타임스탬프 등을 포함
public class ChatDTO {

    private Integer chatId; // 채팅 ID
    private Integer userId; // 사용자 ID
    private String message; // 채팅 메시지
    private String timestamp; // 메시지 전송 시간

    // 기본 생성자
    public ChatDTO() {}

    // Getter 및 Setter
    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

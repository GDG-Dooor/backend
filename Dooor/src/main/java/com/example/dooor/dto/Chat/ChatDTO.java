package com.example.dooor.dto.Chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//데이터베이스에서 채팅 메시지를 조회할 때 사용되며, 채팅 메시지의 ID, 사용자 ID, 메시지 내용, 타임스탬프 등을 포함
@Getter
@Setter
@NoArgsConstructor
public class ChatDTO {

    private Integer chatId; // 채팅 ID
    private Integer userId; // 사용자 ID
    private String message; // 채팅 메시지
    private String timestamp; // 메시지 전송 시간
}

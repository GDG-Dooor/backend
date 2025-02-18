package com.example.dooor.dto.Chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
//사용자가 채팅 메시지를 전송할 때 필요한 정보를 포함합니다. 사용자 ID와 메시지 내용만 필요
public class ChatRequestDTO {

    private Integer user_id; // 사용자 ID
    private String user_input; // 메시지 내용
}

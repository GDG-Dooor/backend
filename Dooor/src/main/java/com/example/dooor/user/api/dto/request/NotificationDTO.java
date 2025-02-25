package com.example.dooor.user.api.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//알림 ID, 사용자 ID, 알림 메시지, 그리고 알림 시간을 포함합니다. 특정 사용자에게 전송할 알림 정보를 클라이언트와 서버 간에 전송하는 데 사용
public class NotificationDTO {

    private Integer notificationId; // 알림 ID
    private Integer userId; // 사용자 ID
    private String message; // 알림 메시지
    private String timestamp; // 알림 시간
}

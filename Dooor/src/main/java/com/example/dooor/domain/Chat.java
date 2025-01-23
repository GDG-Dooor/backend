package com.example.dooor.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chatId; // 채팅 고유 ID

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 해당 채팅을 보낸 사용자

    @Column(name = "message", columnDefinition = "TEXT", nullable = false)
    private String message; // 메시지 내용

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt; // 메시지 생성 시간

    // 생성자
    public Chat(User user, String message) {
        this.user = user;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }
}


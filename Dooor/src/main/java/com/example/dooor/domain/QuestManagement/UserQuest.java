package com.example.dooor.domain.QuestManagement;

import com.example.dooor.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserQuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userQuestId; // 유저-퀘스트 관계 고유 ID

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 해당 관계의 사용자

    @ManyToOne
    @JoinColumn(name = "quest_id")
    private Quest quest; // 해당 관계의 퀘스트

    @Column(name = "status", columnDefinition = "ENUM('클리어', '미완료') DEFAULT '미완료'")
    private String status; // 유저-퀘스트 상태

    @Column(name = "photo")
    private String photo; // 퀘스트 인증 사진 경로

    @Column(name = "verified", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean verified; // 인증 여부

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt; // 마지막 업데이트 날짜
}

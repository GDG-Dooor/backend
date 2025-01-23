package com.example.dooor.domain.QuestManagement;

import com.example.dooor.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questId; // 퀘스트 고유 ID

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 해당 퀘스트를 소유한 사용자

    @Column(name = "title", nullable = false, length = 100)
    private String title; // 퀘스트 제목

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // 퀘스트 설명

    @Column(name = "current_stage", columnDefinition = "INT DEFAULT 1")
    private Integer currentStage; // 현재 진행 중인 단계

    @Column(name = "status", columnDefinition = "ENUM('진행 중', '완료') DEFAULT '진행 중'")
    private String status; // 퀘스트 상태

    @OneToMany(mappedBy = "quest")
    private List<Stage> stages; // 해당 퀘스트의 단계들

    // 생성자
    public Quest(User user, String title, String description, Integer currentStage, String status) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.currentStage = currentStage != null ? currentStage : 1;
        this.status = status != null ? status : "진행 중";
    }
}

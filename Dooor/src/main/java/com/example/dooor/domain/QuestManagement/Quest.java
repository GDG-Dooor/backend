package com.example.dooor.domain.QuestManagement;

import com.example.dooor.domain.User;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Quest")
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

    // 기본 생성자
    public Quest() {}

    // 생성자
    public Quest(User user, String title, String description, Integer currentStage, String status) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.currentStage = currentStage != null ? currentStage : 1;
        this.status = status != null ? status : "진행 중";
    }

    // Getter 및 Setter
    public Integer getQuestId() {
        return questId;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Integer currentStage) {
        this.currentStage = currentStage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }
}

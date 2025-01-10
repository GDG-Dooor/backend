package com.example.dooor.domain.QuestManagement;

import com.example.dooor.domain.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "UserQuest")
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

    @Column(name = "photo", length = 255)
    private String photo; // 퀘스트 인증 사진 경로

    @Column(name = "verified", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean verified; // 인증 여부

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt; // 마지막 업데이트 날짜

    // 기본 생성자
    public UserQuest() {}

    // 생성자
    public UserQuest(User user, Quest quest, String status, String photo, Boolean verified) {
        this.user = user;
        this.quest = quest;
        this.status = status != null ? status : "미완료";
        this.photo = photo;
        this.verified = verified != null ? verified : false;
        this.updatedAt = LocalDateTime.now();
    }

    // Getter 및 Setter
    public Integer getUserQuestId() {
        return userQuestId;
    }

    public void setUserQuestId(Integer userQuestId) {
        this.userQuestId = userQuestId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

package com.example.dooor.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Ranking")
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rankingId; // 랭킹 고유 ID

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 해당 랭킹의 사용자

    @Column(name = "score", columnDefinition = "INT DEFAULT 0")
    private Integer score; // 사용자 점수

    @Column(name = "`rank`")
    private Integer rank; // 사용자 랭킹

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt; // 마지막 업데이트 시간

    // 기본 생성자
    public Ranking() {}

    // 생성자
    public Ranking(User user, Integer score, Integer rank) {
        this.user = user;
        this.score = score != null ? score : 0;
        this.rank = rank;
        this.updatedAt = LocalDateTime.now();
    }

    // Getter 및 Setter
    public Integer getRankingId() {
        return rankingId;
    }

    public void setRankingId(Integer rankingId) {
        this.rankingId = rankingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}


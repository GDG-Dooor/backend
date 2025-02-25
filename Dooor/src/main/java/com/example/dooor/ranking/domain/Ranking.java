package com.example.dooor.ranking.domain;

import com.example.dooor.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    // 생성자
    public Ranking(User user, Integer score, Integer rank) {
        this.user = user;
        this.score = score != null ? score : 0;
        this.rank = rank;
        this.updatedAt = LocalDateTime.now();
    }
}


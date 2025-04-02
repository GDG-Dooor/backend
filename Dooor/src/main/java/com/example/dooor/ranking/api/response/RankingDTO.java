package com.example.dooor.ranking.api.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RankingDTO {

    private Integer userId;      // 사용자 ID
    private String nickname;      // 사용자 닉네임
    private Integer score;        // 사용자 점수
    private Integer rank;         // 사용자 랭킹
    private LocalDateTime updatedAt; // 마지막 업데이트 시간


    // 생성자 추가 (선택 사항)
    public RankingDTO(Integer userId, String nickname, Integer score, Integer rank, LocalDateTime updatedAt) {
        this.userId = userId;
        this.nickname = nickname;
        this.score = score;
        this.rank = rank;
        this.updatedAt = updatedAt;
    }

}

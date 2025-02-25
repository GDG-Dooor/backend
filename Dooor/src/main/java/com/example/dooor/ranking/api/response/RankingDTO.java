package com.example.dooor.ranking.api.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RankingDTO {

    private Integer userId; // 사용자 ID
    private String nickname; // 사용자 닉네임
    private Integer score; // 사용자 점수
    private Integer rank; // 사용자 랭킹
}

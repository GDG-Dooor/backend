package com.example.dooor.dto;

public class RankingDTO {

    private Integer userId; // 사용자 ID
    private String nickname; // 사용자 닉네임
    private Integer score; // 사용자 점수
    private Integer rank; // 사용자 랭킹

    // 기본 생성자
    public RankingDTO() {}

    // Getter 및 Setter
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
}

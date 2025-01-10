package com.example.dooor.dto.User;

public class UserProfileDTO {

    private Integer userId; // 사용자 ID
    private String nickname; // 사용자 닉네임
    private String email; // 사용자 이메일
    private String phoneNumber; // 사용자 전화번호
    private Integer rank; // 사용자 랭킹 점수

    // 기본 생성자
    public UserProfileDTO() {}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}

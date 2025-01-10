package com.example.dooor.domain;

import com.example.dooor.domain.QuestManagement.Grade;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId; // 사용자 고유 ID

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname; // 닉네임

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email; // 이메일 주소

    @Column(name = "password", nullable = false, length = 255)
    private String password; // 비밀번호 (암호화 필요)

    @Column(name = "phone_number", length = 15)
    private String phoneNumber; // 전화번호

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt; // 가입 날짜

    @Column(name = "rank", columnDefinition = "INT DEFAULT 0")
    private Integer rank; // 랭킹 점수

    @ManyToOne
    @JoinColumn(name = "current_grade_id")
    private Grade currentGrade; // 현재 등급 (Grade와의 관계)

    @Column(name = "google_id", unique = true, length = 255)
    private String googleId; // 구글 계정 ID

    @Column(name = "provider", length = 50)
    private String provider; // 인증 제공자 (예: "google")

    // 기본 생성자
    public User() {}

    // 생성자
    public User(String nickname, String email, String password, String phoneNumber, LocalDateTime createdAt, Integer rank, String googleId, String provider) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.rank = rank != null ? rank : 0;
        this.googleId = googleId; // 구글 ID 초기화
        this.provider = provider; // 인증 제공자 초기화
    }

    // 사용자 ID를 설정할 수 있는 생성자 추가
    public User(Integer userId) {
        this.userId = userId; // 사용자 ID 설정
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Grade getCurrentGrade() {
        return currentGrade;
    }

    public void setCurrentGrade(Grade currentGrade) {
        this.currentGrade = currentGrade;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}

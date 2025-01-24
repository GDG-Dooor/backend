package com.example.dooor.domain;

import com.example.dooor.domain.QuestManagement.Grade;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId; // 사용자 고유 ID

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname; // 닉네임

    @Column(name = "email", nullable = false, unique = true)
    private String email; // 이메일 주소

    @Column(name = "password", nullable = false)
    private String password; // 비밀번호 (암호화 필요)

    @Column(name = "phone_number", length = 15)
    private String phoneNumber; // 전화번호

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now(); // 가입 날짜

    @Column(name = "`rank`", columnDefinition = "INT DEFAULT 0")
    private Integer rank = 0; // 랭킹 점수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_grade_id")
    private Grade currentGrade; // 현재 등급 (Grade와의 관계)

    @Column(name = "google_id", unique = true)
    private String googleId; // 구글 계정 ID

    @Column(name = "provider", length = 50)
    private String provider; // 인증 제공자 (예: "google")

    // 생성자
    public User(String nickname, String email, String password, String phoneNumber, LocalDateTime createdAt, Integer rank, String googleId, String provider) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.rank = rank != null ? rank : 0;
        this.googleId = googleId;
        this.provider = provider;
    }

    // 사용자 ID를 설정할 수 있는 생성자 추가
    public User(Integer userId) {
        this.userId = userId;
    }
}

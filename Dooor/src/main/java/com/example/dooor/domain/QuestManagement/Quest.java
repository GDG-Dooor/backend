package com.example.dooor.domain.QuestManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_id")
    private Integer questId; // 퀘스트 고유 ID

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user; // 해당 퀘스트를 소유한 사용자

    @Column(name = "title", nullable = false, length = 100)
    private String title; // 퀘스트 이름

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // 퀘스트 설명

    @Column(name = "need_image")
    private boolean needImage;
//    @Column(name = "status", columnDefinition = "ENUM('진행 중', '완료') DEFAULT '진행 중'")
//    private String status; // 퀘스트 상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id")
    private Stage stage; // 해당 퀘스트의 단계
}

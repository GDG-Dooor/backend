package com.example.dooor.domain.QuestManagement;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stage {

    @Id
    private Integer id; // 단계 번호

    @Column(name = "stage_title")
    private String title; // 단계 이름

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // 단계 설명

    @OneToMany(mappedBy = "stage", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quest> quests; // 해당 단계가 속한 퀘스트
}

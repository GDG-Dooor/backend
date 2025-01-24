package com.example.dooor.domain.QuestManagement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stageId; // 단계 고유 ID

    @ManyToOne
    @JoinColumn(name = "quest_id")
    private Quest quest; // 해당 단계가 속한 퀘스트

    @Column(name = "stage_number", nullable = false)
    private Integer stageNumber; // 단계 번호

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // 단계 설명

    // 생성자
    public Stage(Quest quest, Integer stageNumber, String description) {
        this.quest = quest;
        this.stageNumber = stageNumber;
        this.description = description;
    }
}

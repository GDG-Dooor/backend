package com.example.dooor.domain.QuestManagement;

import jakarta.persistence.*;

@Entity
@Table(name = "Stage")
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

    // 기본 생성자
    public Stage() {}

    // 생성자
    public Stage(Quest quest, Integer stageNumber, String description) {
        this.quest = quest;
        this.stageNumber = stageNumber;
        this.description = description;
    }

    // Getter 및 Setter
    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public Integer getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(Integer stageNumber) {
        this.stageNumber = stageNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

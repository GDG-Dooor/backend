package com.example.dooor.dto;

public class StageDTO {

    private Integer stageId; // 단계 ID
    private String title; // 단계 제목
    private String description; // 단계 설명
    private Integer questId; // 관련 퀘스트 ID
    private String status; // 단계 상태 (예: 진행 중, 완료 등)

    // 기본 생성자
    public StageDTO() {}

    // Getter 및 Setter
    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuestId() {
        return questId;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

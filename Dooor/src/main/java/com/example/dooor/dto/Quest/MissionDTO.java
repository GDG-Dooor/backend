package com.example.dooor.dto.Quest;

public class MissionDTO {

    private Integer missionId; // 미션 ID
    private String title; // 미션 제목
    private String description; // 미션 설명
    private Integer stageId; // 관련 단계 ID
    private String status; // 미션 상태 (예: 진행 중, 완료 등)

    // 기본 생성자
    public MissionDTO() {}

    // Getter 및 Setter
    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
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

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

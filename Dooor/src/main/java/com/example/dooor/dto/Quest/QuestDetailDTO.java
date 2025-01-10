package com.example.dooor.dto.Quest;

import com.example.dooor.dto.StageDTO;

import java.util.List;
//특정 퀘스트의 상세 정보 전송 DTO
public class QuestDetailDTO {

    private Integer questId; // 퀘스트 ID
    private String title; // 퀘스트 제목
    private String description; // 퀘스트 설명
    private List<StageDTO> stages; // 단계 목록

    // 기본 생성자
    public QuestDetailDTO() {}

    // Getter 및 Setter
    public Integer getQuestId() {
        return questId;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
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

    public List<StageDTO> getStages() {
        return stages;
    }

    public void setStages(List<StageDTO> stages) {
        this.stages = stages;
    }
}

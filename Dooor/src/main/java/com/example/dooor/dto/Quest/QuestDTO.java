package com.example.dooor.dto.Quest;

public class QuestDTO {

    private Integer questId; // 퀘스트 ID
    private String title; // 퀘스트 제목
    private String description; // 퀘스트 설명
    private String status; // 퀘스트 상태 (예: 진행 중, 완료 등)

    // 기본 생성자
    public QuestDTO() {}

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

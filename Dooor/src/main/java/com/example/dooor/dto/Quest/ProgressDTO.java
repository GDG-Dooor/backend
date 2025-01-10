package com.example.dooor.dto.Quest;

import java.util.List;
//사용자 퀘스트 진행 상태 DTO
public class ProgressDTO {

    private Integer userId; // 사용자 ID
    private List<QuestProgress> questProgressList; // 퀘스트 진행 목록

    // 기본 생성자
    public ProgressDTO() {}

    // Getter 및 Setter
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<QuestProgress> getQuestProgressList() {
        return questProgressList;
    }

    public void setQuestProgressList(List<QuestProgress> questProgressList) {
        this.questProgressList = questProgressList;
    }

    // 내부 클래스: 퀘스트 진행 상태
    public static class QuestProgress {
        private Integer questId; // 퀘스트 ID
        private String title; // 퀘스트 제목
        private String status; // 퀘스트 상태

        // 기본 생성자
        public QuestProgress() {}

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}

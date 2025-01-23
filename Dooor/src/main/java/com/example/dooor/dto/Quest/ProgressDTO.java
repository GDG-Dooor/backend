package com.example.dooor.dto.Quest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
//사용자 퀘스트 진행 상태 DTO
public class ProgressDTO {

    private Integer userId; // 사용자 ID
    private List<QuestProgress> questProgressList; // 퀘스트 진행 목록

    // 내부 클래스: 퀘스트 진행 상태
    @Getter
    @Setter
    @NoArgsConstructor
    public static class QuestProgress {
        private Integer questId; // 퀘스트 ID
        private String title; // 퀘스트 제목
        private String status; // 퀘스트 상태
    }
}

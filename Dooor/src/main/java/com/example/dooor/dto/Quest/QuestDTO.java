package com.example.dooor.dto.Quest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestDTO {

    private Integer questId; // 퀘스트 ID
    private String title; // 퀘스트 제목
    private String description; // 퀘스트 설명
    private String status; // 퀘스트 상태 (예: 진행 중, 완료 등)
}

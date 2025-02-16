package com.example.dooor.dto.Quest;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class QuestRes {

    private Integer questId; // 퀘스트 ID
    private String title; // 퀘스트 제목
    private String description; // 퀘스트 설명
    private Integer stageId; // 단계 번호
    private boolean needImage; // 사진 필요여부
}

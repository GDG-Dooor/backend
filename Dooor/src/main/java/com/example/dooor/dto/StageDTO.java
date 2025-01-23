package com.example.dooor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StageDTO {

    private Integer stageId; // 단계 ID
    private String title; // 단계 제목
    private String description; // 단계 설명
    private Integer questId; // 관련 퀘스트 ID
    private String status; // 단계 상태 (예: 진행 중, 완료 등)
}

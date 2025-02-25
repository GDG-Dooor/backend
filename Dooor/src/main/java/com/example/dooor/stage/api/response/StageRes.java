package com.example.dooor.stage.api.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class StageRes {

    private Integer stageId; // 단계 ID
    private String title; // 단계 제목
    private String description; // 단계 설명
    private List<Integer> questId; // 관련 퀘스트 ID
}

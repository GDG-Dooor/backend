package com.example.dooor.dto.Quest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MissionDTO {

    private Integer missionId; // 미션 ID
    private String title; // 미션 제목
    private String description; // 미션 설명
    private Integer stageId; // 관련 단계 ID
    private String status; // 미션 상태 (예: 진행 중, 완료 등)
}

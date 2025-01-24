package com.example.dooor.dto.Quest;

import com.example.dooor.dto.StageDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
//특정 퀘스트의 상세 정보 전송 DTO
public class QuestDetailDTO {

    private Integer questId; // 퀘스트 ID
    private String title; // 퀘스트 제목
    private String description; // 퀘스트 설명
    private List<StageDTO> stages; // 단계 목록
}

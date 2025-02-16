package com.example.dooor.service;

import com.example.dooor.domain.QuestManagement.Quest;
import com.example.dooor.domain.QuestManagement.Stage;
import com.example.dooor.dto.Stage.StageReq;
import com.example.dooor.dto.Stage.StageRes;
import com.example.dooor.repository.StageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StageService {
    private final StageRepository stageRepository;

    public StageRes mkStage(StageReq stageReq) {
        Stage stage = Stage.builder()
                .id(stageReq.getStageId())
                .title(stageReq.getTitle())
                .description(stageReq.getDescription())
                .build();

        stageRepository.save(stage);
        return StageRes.builder()
                .stageId(stageReq.getStageId())
                .title(stageReq.getTitle())
                .description(stageReq.getDescription())
                .build();
    }

    public List<StageRes> getAllStages() {
        List<Stage> stages = stageRepository.findAll();
        return stages.stream()
                .map(stage -> StageRes.builder()
                        .stageId(stage.getId())
                        .title(stage.getTitle())
                        .description(stage.getDescription())
                        .questId(stage.getQuests().stream().map(Quest::getQuestId).toList())
                        .build())
                .toList();
    }

    public StageRes getStageById(int id) {
        Stage stage = stageRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return StageRes.builder()
                .stageId(stage.getId())
                .title(stage.getTitle())
                .description(stage.getDescription())
                .questId(stage.getQuests().stream().map(Quest::getQuestId).toList())
                .build();
    }

    public void deleteStageById(int id) {
        stageRepository.deleteById(id);
    }
}

package com.example.dooor.stage.application;

import com.example.dooor.quest.domain.Quest;
import com.example.dooor.stage.domain.Stage;
import com.example.dooor.stage.api.request.StageReq;
import com.example.dooor.stage.api.response.StageRes;
import com.example.dooor.stage.domain.repository.StageRepository;
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

    public StageRes getStageById(Integer id) {
        Stage stage = stageRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return StageRes.builder()
                .stageId(stage.getId())
                .title(stage.getTitle())
                .description(stage.getDescription())
                .questId(stage.getQuests().stream().map(Quest::getQuestId).toList())
                .build();
    }

    public StageRes updateStage(StageReq stageReq) {
        Stage stage = stageRepository.findById(stageReq.getStageId()).orElseThrow(IllegalArgumentException::new);
        stageRepository.save(Stage.builder()
                .id(stageReq.getStageId())
                .title(stageReq.getTitle())
                .description(stageReq.getDescription())
                .build());
        return StageRes.builder()
                .stageId(stageReq.getStageId())
                .title(stageReq.getTitle())
                .description(stageReq.getDescription())
                .questId(stage.getQuests().stream().map(Quest::getQuestId).toList())
                .build();
    }

    public void deleteStageById(Integer stageId) {
        stageRepository.deleteById(stageId);
    }
}

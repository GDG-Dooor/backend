package com.example.dooor.controller;

import com.example.dooor.dto.Stage.StageReq;
import com.example.dooor.dto.Stage.StageRes;
import com.example.dooor.repository.StageRepository;
import com.example.dooor.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stage")
@RequiredArgsConstructor
public class StageController {

    private final StageService stageService;
    private final StageRepository stageRepository;

    @PostMapping("/make")
    public ResponseEntity<StageRes> saveStage(@RequestBody StageReq stageReq) {
        if(stageRepository.findById(stageReq.getStageId()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        StageRes stageRes = stageService.mkStage(stageReq);
        return ResponseEntity.ok(stageRes);
    }

    @GetMapping
    public ResponseEntity<List<StageRes>> getAllStages() {
        return ResponseEntity.ok(stageService.getAllStages());
    }

    @GetMapping("/{stageId}")
    public ResponseEntity<StageRes> getStageById(@PathVariable int stageId) {
        if(stageRepository.findById(stageId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stageService.getStageById(stageId));
    }
}

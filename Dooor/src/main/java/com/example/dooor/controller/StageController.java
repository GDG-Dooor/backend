package com.example.dooor.controller;

import com.example.dooor.dto.Stage.StageReq;
import com.example.dooor.dto.Stage.StageRes;
import com.example.dooor.repository.StageRepository;
import com.example.dooor.service.StageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stage")
@RequiredArgsConstructor
@Tag(name = "Stage", description = "단계 추가, 정보확인 등의 API")
public class StageController {

    private final StageService stageService;
    private final StageRepository stageRepository;

    @PostMapping("/make")
    @Operation(summary = "단계 생성", description = "새로운 단계를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "단계가 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 단계 ID입니다.")
    })
    public ResponseEntity<StageRes> saveStage(@RequestBody StageReq stageReq) {
        if(stageRepository.findById(stageReq.getStageId()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        StageRes stageRes = stageService.mkStage(stageReq);
        return ResponseEntity.ok(stageRes);
    }

    @GetMapping
    @Operation(summary = "전체 단계 조회", description = "모든 단계를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "단계 목록을 성공적으로 반환했습니다.")
    })
    public ResponseEntity<List<StageRes>> getAllStages() {
        return ResponseEntity.ok(stageService.getAllStages());
    }

    @GetMapping("/{stageId}")
    @Operation(summary = "특정 단계 조회", description = "주어진 ID의 단계를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "단계 정보를 성공적으로 반환했습니다."),
            @ApiResponse(responseCode = "404", description = "단계를 찾을 수 없습니다.")
    })
    public ResponseEntity<StageRes> getStageById(@PathVariable int stageId) {
        if(stageRepository.findById(stageId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stageService.getStageById(stageId));
    }
}

package com.example.dooor.quest.api;

import com.example.dooor.quest.api.dto.request.QuestReq;
import com.example.dooor.quest.api.dto.response.QuestRes;
import com.example.dooor.quest.api.dto.response.UserQuestMapping;
import com.example.dooor.quest.domain.repository.QuestRepository;
import com.example.dooor.quest.application.QuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/quests")
@RequiredArgsConstructor
@Tag(name = "Quest", description = "퀘스트 추가, 정보확인 등의 API")
public class QuestController {

    private final QuestService questService;
    private final QuestRepository questRepository;

    @PostMapping("/make")
    @Operation(summary = "퀘스트 생성", description = "새로운 퀘스트를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퀘스트가 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    public ResponseEntity<QuestRes> makeQuest(@RequestBody QuestReq questReq) {
        QuestRes questRes = questService.mkQuest(questReq);
        return ResponseEntity.ok(questRes);
    }

    // 퀘스트 목록 조회
    @GetMapping
    @Operation(summary = "퀘스트 목록 조회", description = "모든 퀘스트의 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퀘스트 목록을 성공적으로 반환했습니다.")
    })
    public ResponseEntity<List<QuestRes>> getAllQuests() {
        return ResponseEntity.ok(questService.getAllQuests()); // 퀘스트 목록 반환
    }

    // 특정 퀘스트 정보 조회
    @GetMapping("/{questId}")
    @Operation(summary = "특정 퀘스트 정보 조회", description = "주어진 ID의 퀘스트 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퀘스트 정보를 성공적으로 반환했습니다."),
            @ApiResponse(responseCode = "404", description = "퀘스트를 찾을 수 없습니다.")
    })
    public ResponseEntity<QuestRes> getQuestById(@PathVariable Integer questId) {
        if (questRepository.findById(questId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questService.getQuestById(questId));
    }

    @PostMapping("/start")
    @Operation(summary = "퀘스트 시작", description = "사용자가 퀘스트를 시작합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퀘스트가 성공적으로 시작되었습니다."),
            @ApiResponse(responseCode = "404", description = "사용자 또는 퀘스트를 찾을 수 없습니다.")
    })
    public ResponseEntity<UserQuestMapping> startQuest(@RequestParam Integer userId, @RequestParam Integer questId) {
        UserQuestMapping mapping = questService.startQuest(userId, questId);
        return ResponseEntity.ok(mapping);
    }

    @PostMapping("/complete")
    @Operation(summary = "퀘스트 완료", description = "사용자가 퀘스트를 완료합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퀘스트 완료 상태를 반환합니다."),
            @ApiResponse(responseCode = "404", description = "사용자 또는 퀘스트를 찾을 수 없습니다.")
    })
    public ResponseEntity<String> completeQuest(@RequestParam Integer userId, @RequestParam Integer questId) {
        boolean completed = questService.completeQuest(userId, questId);
        return completed ? ResponseEntity.ok("true") : ResponseEntity.ok("false");
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateQuest(@RequestPart MultipartFile image, Principal principal) throws IOException, ParseException {
        return ResponseEntity.ok(questService.validateQuest(image, principal));
//        return questService.validateQuest(image, principal);
    }

    @PatchMapping("/update")
    public ResponseEntity<QuestRes> updateQuest(@RequestBody QuestReq questReq) {
        if(questRepository.findById(questReq.getQuestId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questService.updateQuest(questReq));
    }

    @DeleteMapping("/{questId}")
    public ResponseEntity<Integer> deleteQuestById(@PathVariable Integer questId) {
        questService.deleteQuestById(questId);
        return ResponseEntity.ok(questId);
    }
//    // 퀘스트 시작
//    @PostMapping("/start")
//    public ResponseEntity<UserQuest> startQuest(@RequestBody QuestRes questRes, @RequestParam Integer userId) {
//        UserQuest userQuest = questService.startQuest(userId, questRes);
//        return ResponseEntity.ok(userQuest); // 사용자-퀘스트 반환
//    }
//
//    // 퀘스트 완료 처리
//    @PostMapping("/complete")
//    public ResponseEntity<Void> completeQuest(@RequestParam Integer userQuestId) {
//        boolean completed = questService.completeQuest(userQuestId);
//        return completed ? ResponseEntity.ok().build() // 완료 처리 성공
//                : ResponseEntity.notFound().build(); // 사용자-퀘스트 없음
//    }
//
//    // 이미지 검증
//    @PostMapping("/verify-image")
//    public ResponseEntity<Void> verifyImage(@RequestParam Integer userQuestId, @RequestParam String imageUrl) {
//        boolean verified = questService.verifyImage(userQuestId, imageUrl);
//        return verified ? ResponseEntity.ok().build() // 이미지 검증 성공
//                : ResponseEntity.notFound().build(); // 사용자-퀘스트 없음
//    }
}

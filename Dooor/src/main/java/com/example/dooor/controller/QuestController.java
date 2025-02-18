package com.example.dooor.controller;

import com.example.dooor.dto.Quest.QuestReq;
import com.example.dooor.dto.Quest.QuestRes;
import com.example.dooor.dto.Quest.UserQuestMapping;
import com.example.dooor.repository.QuestRepository;
import com.example.dooor.service.QuestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quests")
@RequiredArgsConstructor
@Tag(name = "Quest", description = "퀘스트 추가, 정보확인 등의 API")
public class QuestController {

    private final QuestService questService;
    private final QuestRepository questRepository;

    @PostMapping("/make")
    public ResponseEntity<QuestRes> makeQuest(@RequestBody QuestReq questReq) {
        QuestRes questRes = questService.mkQuest(questReq);
        return ResponseEntity.ok(questRes);
    }

    // 퀘스트 목록 조회
    @GetMapping
    public ResponseEntity<List<QuestRes>> getAllQuests() {
        return ResponseEntity.ok(questService.getAllQuests()); // 퀘스트 목록 반환
    }

    // 특정 퀘스트 정보 조회
    @GetMapping("/{questId}")
    public ResponseEntity<QuestRes> getQuestById(@PathVariable Integer questId) {
        if(questRepository.findById(questId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questService.getQuestById(questId));
    }

    @PostMapping("/start")
    public ResponseEntity<UserQuestMapping> startQuest(@RequestParam Integer userId, @RequestParam Integer questId) {
        UserQuestMapping mapping = questService.startQuest(userId, questId);
        return ResponseEntity.ok(mapping);
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completeQuest(@RequestParam Integer userId, @RequestParam Integer questId) {
        boolean completed = questService.completeQuest(userId, questId);
        return completed ? ResponseEntity.ok("true") : ResponseEntity.ok("false");
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

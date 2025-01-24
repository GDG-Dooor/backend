package com.example.dooor.controller;

import com.example.dooor.domain.QuestManagement.Quest;
import com.example.dooor.domain.QuestManagement.UserQuest;
import com.example.dooor.dto.Quest.QuestDTO;
import com.example.dooor.service.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quests")
@RequiredArgsConstructor
public class QuestController {

    private final QuestService questService;

    // 퀘스트 목록 조회
    @GetMapping
    public ResponseEntity<List<Quest>> getAllQuests() {
        List<Quest> quests = questService.getAllQuests();
        return ResponseEntity.ok(quests); // 퀘스트 목록 반환
    }

    // 특정 퀘스트 정보 조회
    @GetMapping("/{questId}")
    public ResponseEntity<Quest> getQuestById(@PathVariable Integer questId) {
        Optional<Quest> questOptional = questService.getQuestById(questId);
        return questOptional.map(ResponseEntity::ok) // 퀘스트가 존재하면 반환
                .orElseGet(() -> ResponseEntity.notFound().build()); // 없으면 404 반환
    }

    // 퀘스트 시작
    @PostMapping("/start")
    public ResponseEntity<UserQuest> startQuest(@RequestBody QuestDTO questDTO, @RequestParam Integer userId) {
        UserQuest userQuest = questService.startQuest(userId, questDTO);
        return ResponseEntity.ok(userQuest); // 사용자-퀘스트 반환
    }

    // 퀘스트 완료 처리
    @PostMapping("/complete")
    public ResponseEntity<Void> completeQuest(@RequestParam Integer userQuestId) {
        boolean completed = questService.completeQuest(userQuestId);
        return completed ? ResponseEntity.ok().build() // 완료 처리 성공
                : ResponseEntity.notFound().build(); // 사용자-퀘스트 없음
    }

    // 이미지 검증
    @PostMapping("/verify-image")
    public ResponseEntity<Void> verifyImage(@RequestParam Integer userQuestId, @RequestParam String imageUrl) {
        boolean verified = questService.verifyImage(userQuestId, imageUrl);
        return verified ? ResponseEntity.ok().build() // 이미지 검증 성공
                : ResponseEntity.notFound().build(); // 사용자-퀘스트 없음
    }
}

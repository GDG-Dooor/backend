package com.example.dooor.controller;

import com.example.dooor.domain.Ranking;
import com.example.dooor.service.RankingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
@Tag(name = "Ranking", description = "랭킹 API, 아직 미완")
public class RankingController {

    private final RankingService rankingService;

    // 전체 랭킹 조회
    @GetMapping("/all")
    public ResponseEntity<List<Ranking>> getAllRankings() {
        List<Ranking> rankings = rankingService.getAllRankings();
        return ResponseEntity.ok(rankings); // 전체 랭킹 반환
    }

    // 사용자 개인 랭킹 조회
    @GetMapping
    public ResponseEntity<Ranking> getUserRanking(@RequestParam Integer userId) {
        Optional<Ranking> rankingOptional = rankingService.getUserRanking(userId);
        return rankingOptional.map(ResponseEntity::ok) // 랭킹 존재 시 반환
                .orElseGet(() -> ResponseEntity.notFound().build()); // 랭킹 없음
    }

    // 랭킹 점수 업데이트
    @PostMapping
    public ResponseEntity<Void> updateRankingScore(@RequestParam Integer userId, @RequestParam Integer newScore) {
        boolean updated = rankingService.updateRankingScore(userId, newScore);
        return updated ? ResponseEntity.ok().build() // 점수 업데이트 성공
                : ResponseEntity.notFound().build(); // 사용자 랭킹 없음
    }
}

package com.example.dooor.ranking.api;

import com.example.dooor.ranking.domain.Ranking;
import com.example.dooor.ranking.application.RankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
@Tag(name = "Ranking", description = "랭킹 API")
public class RankingController {

    private final RankingService rankingService;

    // 전체 랭킹 조회
    @GetMapping("/all")
    @Operation(summary = "전체 랭킹 조회", description = "모든 사용자의 랭킹을 점수 기준으로 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 랭킹을 성공적으로 반환했습니다."),
            @ApiResponse(responseCode = "404", description = "랭킹이 존재하지 않습니다.")
    })
    public ResponseEntity<List<Ranking>> getAllRankings() {
        List<Ranking> rankings = rankingService.getAllRankings();
        return ResponseEntity.ok(rankings); // 전체 랭킹 반환
    }

    // 사용자 개인 랭킹 조회
    @GetMapping("/user")
    @Operation(summary = "사용자 개인 랭킹 조회", description = "주어진 사용자 ID에 대한 개인 랭킹을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 랭킹을 성공적으로 반환했습니다."),
            @ApiResponse(responseCode = "404", description = "사용자 랭킹을 찾을 수 없습니다.")
    })
    public ResponseEntity<Ranking> getUserRanking(@RequestParam Integer userId) {
        Optional<Ranking> rankingOptional = rankingService.getUserRanking(userId);
        return rankingOptional.map(ResponseEntity::ok) // 랭킹 존재 시 반환
                .orElseGet(() -> ResponseEntity.notFound().build()); // 랭킹 없음
    }

    // 랭킹 점수 업데이트
    @PostMapping("/update")
    @Operation(summary = "랭킹 점수 업데이트", description = "사용자 ID를 이용해 랭킹 점수를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "랭킹 점수가 성공적으로 업데이트되었습니다."),
            @ApiResponse(responseCode = "404", description = "사용자 랭킹을 찾을 수 없습니다.")
    })
    public ResponseEntity<Void> updateRankingScore(@RequestParam Integer userId) {
        boolean updated = rankingService.updateRankingScore(userId, 10); // 10점을 추가
        return updated ? ResponseEntity.ok().build() // 점수 업데이트 성공
                : ResponseEntity.notFound().build(); // 사용자 랭킹 없음
    }
}

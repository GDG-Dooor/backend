package com.example.dooor.ranking.application;

import com.example.dooor.ranking.domain.Ranking;
import com.example.dooor.ranking.domain.repository.RankingRepository;
import com.example.dooor.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;
    private final UserRepository userRepository;
    private final RedisRankingService redisRankingService;

    // 전체 랭킹 조회
    public List<Ranking> getAllRankings() {
        List<String> topUsers = redisRankingService.getTopRankings(50); // 상위 50명 조회
        return rankingRepository.findByUser_UserIdIn(topUsers.stream()
                .map(Integer::parseInt)
                .toList()); // topUsers를 이용하여 Ranking 객체로 변환 후 반환
    }

    // 사용자 개인 랭킹 조회
    public Optional<Ranking> getUserRanking(Integer userId) {
        List<Ranking> rankings = rankingRepository.findByUser_UserId(userId);
        return rankings.isEmpty() ? Optional.empty() : Optional.of(rankings.get(0)); // 첫 번째 랭킹 반환
    }

    // 랭킹 점수 업데이트
    public boolean updateRankingScore(Integer userId, Integer newScore) {
        List<Ranking> rankings = rankingRepository.findByUser_UserId(userId);
        if (!rankings.isEmpty()) {
            Ranking ranking = rankings.get(0);
            ranking.setScore(newScore); // 새로운 점수 설정
            ranking.setUpdatedAt(LocalDateTime.now()); // 업데이트 시간 설정
            rankingRepository.save(ranking); // 변경 사항 저장
            redisRankingService.updateRanking(userId, newScore); // Redis에도 점수 업데이트
            return true; // 업데이트 성공
        }
        return false; // 사용자 랭킹 없음
    }

    // 1시간마다 랭킹 점수 업데이트
    @Scheduled(fixedRate = 14400000) // 4시간(7200초)마다 실행
    public void updateAllRankings() {
        List<Ranking> allRankings = rankingRepository.findAllByOrderByScoreDesc(); // 데이터베이스에서 랭킹 조회
        for (Ranking ranking : allRankings) {
            redisRankingService.updateRanking(ranking.getUser().getUserId(), ranking.getScore()); // Redis에 랭킹 업데이트
        }
    }
}

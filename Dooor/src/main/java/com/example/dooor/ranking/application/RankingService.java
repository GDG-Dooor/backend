package com.example.dooor.ranking.application;

import com.example.dooor.ranking.domain.Ranking;
import com.example.dooor.ranking.domain.repository.RankingRepository;
import com.example.dooor.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;
    private final RedisRankingService redisRankingService;

    // 전체 랭킹 조회
    public List<Ranking> getAllRankings() {
        // 레디스에서 상위 50명의 사용자 ID를 조회
        List<String> topUsers = redisRankingService.getTopRankings(50);
        // 사용자 ID에 해당하는 Ranking 객체들을 데이터베이스에서 조회
        List<Ranking> rankings = rankingRepository.findByUser_UserIdIn(topUsers.stream()
                .map(Integer::parseInt)
                .toList());

        // 점수를 기준으로 내림차순 정렬
        rankings.sort(Comparator.comparing(Ranking::getScore).reversed());

        // 랭킹 할당
        for (int i = 0; i < rankings.size(); i++) {
            rankings.get(i).setRank(i + 1); // 순위를 1부터 시작하도록 설정
        }

        return rankings; // 정렬된 랭킹 리스트 반환
    }

    // 사용자 개인 랭킹 조회
    public Optional<Ranking> getUserRanking(Integer userId) {
        // 주어진 사용자 ID에 대한 랭킹 정보를 조회
        Ranking ranking = rankingRepository.findByUser_UserId(userId);
        return Optional.ofNullable(ranking); // null 처리
    }

    // 랭킹 점수 업데이트
    public boolean updateRankingScore(Integer userId, Integer addedScore) {
        // 주어진 사용자 ID에 대한 랭킹 정보 조회
        Ranking ranking = rankingRepository.findByUser_UserId(userId);

        if (ranking == null) {
            // 사용자의 랭킹 정보가 없으면 새로 생성
            ranking = new Ranking();
            ranking.setUser(new User(userId)); // 사용자 설정
            ranking.setScore(addedScore); // 새로운 점수 설정
            ranking.setUpdatedAt(LocalDateTime.now()); // 업데이트 시간 설정
            ranking.setRank(1); // 기본 랭킹 설정
        } else {
            // 기존의 랭킹 정보가 있으면 점수 증가
            ranking.setScore(ranking.getScore() + addedScore); // 점수 증가
            ranking.setUpdatedAt(LocalDateTime.now()); // 업데이트 시간 설정
        }

        rankingRepository.save(ranking); // 랭킹 정보 저장

        // 레디스에도 점수 업데이트
        redisRankingService.updateRanking(userId, ranking.getScore()); // Redis에 점수 업데이트

        return true; // 업데이트 성공
    }
}

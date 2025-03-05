package com.example.dooor.ranking.domain.repository;

import com.example.dooor.ranking.domain.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Integer> {
    List<Ranking> findByUser_UserId(Integer userId); // 사용자 ID로 랭킹 검색
    List<Ranking> findAllByOrderByScoreDesc(); // 점수 기준으로 전체 랭킹 조회
    List<Ranking> findByUser_UserIdIn(List<Integer> userIds); // 사용자 ID 목록으로 랭킹 검색 추가
}

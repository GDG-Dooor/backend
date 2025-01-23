package com.example.dooor.repository;

import com.example.dooor.domain.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Integer> {
    List<Ranking> findByUser_UserId(Integer userId); // 사용자 ID로 랭킹 검색
    List<Ranking> findAllByOrderByScoreDesc(); // 점수 기준으로 전체 랭킹 조회
}

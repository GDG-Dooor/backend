package com.example.dooor.repository;

import com.example.dooor.domain.QuestManagement.Quest;
import com.example.dooor.domain.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Integer> {
    List<Ranking> findByUser_UserId(Integer userId); // 사용자 ID로 랭킹 검색
    List<Ranking> findAllByOrderByScoreDesc(); // 점수 기준으로 전체 랭킹 조회
}

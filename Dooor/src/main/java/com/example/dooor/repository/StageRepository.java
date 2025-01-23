package com.example.dooor.repository;

import com.example.dooor.domain.QuestManagement.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StageRepository extends JpaRepository<Stage, Integer> {
    List<Stage> findByQuest_QuestId(Integer questId); // 퀘스트 ID로 단계 목록 검색
}

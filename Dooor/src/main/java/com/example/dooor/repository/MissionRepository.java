package com.example.dooor.repository;

import com.example.dooor.domain.QuestManagement.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Integer> {
    List<Mission> findByStage_StageId(Integer stageId); // 단계 ID로 미션 목록 검색
}

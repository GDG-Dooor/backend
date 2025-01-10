package com.example.dooor.repository;

import com.example.dooor.domain.QuestManagement.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Integer> {
    List<Mission> findByStageId(Integer stageId); // 단계 ID로 미션 목록 검색
}

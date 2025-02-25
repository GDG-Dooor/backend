package com.example.dooor.quest.domain.repository;

import com.example.dooor.quest.domain.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestRepository extends JpaRepository<Quest, Integer> {
//    List<Quest> findByStage_Id(int stageId);
//    List<Quest> findByUser_UserId(Integer userId); // 사용자 ID로 퀘스트 목록 검색
//    List<Quest> findByStatus(String status); // 상태로 퀘스트 검색
}

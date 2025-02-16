package com.example.dooor.repository;

import com.example.dooor.domain.QuestManagement.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestRepository extends JpaRepository<Quest, Integer> {
//    List<Quest> findByStage_Id(int stageId);
//    List<Quest> findByUser_UserId(Integer userId); // 사용자 ID로 퀘스트 목록 검색
//    List<Quest> findByStatus(String status); // 상태로 퀘스트 검색
}

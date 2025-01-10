package com.example.dooor.repository;


import com.example.dooor.domain.QuestManagement.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Integer> {
    List<Quest> findByUserId(Integer userId); // 사용자 ID로 퀘스트 목록 검색
    List<Quest> findByStatus(String status); // 상태로 퀘스트 검색
}

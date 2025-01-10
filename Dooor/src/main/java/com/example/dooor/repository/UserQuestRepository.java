package com.example.dooor.repository;

import com.example.dooor.domain.QuestManagement.UserQuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuestRepository extends JpaRepository<UserQuest, Integer> {
    // 기본적인 CRUD 메서드는 JpaRepository에서 제공
}

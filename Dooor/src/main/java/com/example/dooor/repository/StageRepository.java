package com.example.dooor.repository;

import com.example.dooor.domain.QuestManagement.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, Integer> {
}

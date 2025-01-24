package com.example.dooor.repository;

import com.example.dooor.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findByUser_UserId(Integer userId); // 사용자 ID로 채팅 목록 검색

}

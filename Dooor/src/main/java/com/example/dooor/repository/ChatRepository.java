package com.example.dooor.repository;

import com.example.dooor.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findByUserId(Integer userId); // 사용자 ID로 채팅 목록 검색
}

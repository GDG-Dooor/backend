package com.example.dooor.service;

import com.example.dooor.domain.Chat;
import com.example.dooor.domain.User;
import com.example.dooor.dto.Chat.ChatRequestDTO;
import com.example.dooor.repository.ChatRepository;
import com.example.dooor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository; // 사용자 정보를 조회하기 위한 UserRepository

    // 챗봇과 채팅
    public Chat sendMessage(ChatRequestDTO chatRequestDTO) {
        Optional<User> userOptional = userRepository.findById(chatRequestDTO.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Chat chat = new Chat(user, chatRequestDTO.getMessage());
            return chatRepository.save(chat); // 채팅 저장
        }
        return null; // 사용자 없음
    }

    // 퀘스트 진행 현황에 따른 메시지
    public String getPersonalizedMessage(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            // 여기에서 사용자 퀘스트 진행 상태를 조회하여 personalized 메시지를 생성하는 로직을 구현합니다.
            return "사용자의 퀘스트 진행 상태에 따른 메시지"; // 예시 메시지 반환
        }
        return "사용자를 찾을 수 없습니다."; // 사용자 없음
    }
}

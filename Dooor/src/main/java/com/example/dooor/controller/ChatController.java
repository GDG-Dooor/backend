package com.example.dooor.controller;

import com.example.dooor.domain.Chat;
import com.example.dooor.dto.Chat.ChatRequestDTO;
import com.example.dooor.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    // 챗봇과 채팅
//    @PostMapping("/message")
//    public ResponseEntity<Chat> sendMessage(@RequestBody ChatRequestDTO chatRequestDTO) {
//        Chat chat = chatService.sendMessage(chatRequestDTO);
//        if (chat != null) {
//            return ResponseEntity.ok(chat); // 채팅 저장 성공
//        }
//        return ResponseEntity.badRequest().build(); // 사용자 없음
//    }

    // 퀘스트 진행 현황에 따른 메시지
    @PostMapping("/personalized")
    public ResponseEntity<String> getPersonalizedMessage(@RequestParam Integer userId) {
        String message = chatService.getPersonalizedMessage(userId);
        return ResponseEntity.ok(message); // 메시지 반환
    }

    @GetMapping("/message")
    public ResponseEntity<String> getMessage(@RequestBody ChatRequestDTO chatRequestDTO, Principal principal) throws JsonProcessingException {
        return ResponseEntity.ok(chatService.getMessage(chatRequestDTO, principal));
    }
}

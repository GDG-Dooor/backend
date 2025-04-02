package com.example.dooor.chat.api;

import com.example.dooor.chat.api.request.ChatRequestDTO;
import com.example.dooor.chat.application.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "Chat", description = "챗봇과의 채팅 API")
public class ChatController {

    private final ChatService chatService;

    /*
    // 챗봇과 채팅
    @PostMapping("/message")
    @Operation(summary = "챗봇과 메시지 전송", description = "사용자가 챗봇에 메시지를 보냅니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메시지가 성공적으로 전송되었습니다."),
            @ApiResponse(responseCode = "400", description = "사용자가 없습니다.")
    })
    public ResponseEntity<Chat> sendMessage(@RequestBody ChatRequestDTO chatRequestDTO) {
        Chat chat = chatService.sendMessage(chatRequestDTO);
        if (chat != null) {
            return ResponseEntity.ok(chat); // 채팅 저장 성공
        }
        return ResponseEntity.badRequest().build(); // 사용자 없음
    }
    */

    // 퀘스트 진행 현황에 따른 메시지
    @PostMapping("/personalized")
    @Operation(summary = "개인화된 메시지 가져오기", description = "사용자 ID에 따라 개인화된 메시지를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "개인화된 메시지를 성공적으로 반환했습니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    public ResponseEntity<String> getPersonalizedMessage(@RequestParam Integer userId) {
        String message = chatService.getPersonalizedMessage(userId);
        return ResponseEntity.ok(message); // 메시지 반환
    }

    @GetMapping("/message")
    @Operation(summary = "메시지 가져오기", description = "사용자가 보낸 메시지를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메시지를 성공적으로 가져왔습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    public ResponseEntity<String> getMessage(ChatRequestDTO chatRequestDTO, Principal principal) throws JsonProcessingException {
        return ResponseEntity.ok(chatService.getMessage(chatRequestDTO, principal));
    }
}

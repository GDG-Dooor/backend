package com.example.dooor.service;

import com.example.dooor.domain.Chat;
import com.example.dooor.domain.User;
import com.example.dooor.dto.Chat.ChatRequestDTO;
import com.example.dooor.repository.ChatRepository;
import com.example.dooor.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository; // 사용자 정보를 조회하기 위한 UserRepository
    private final ObjectMapper objectMapper;

//    // 챗봇과 채팅
//    public Chat sendMessage(ChatRequestDTO chatRequestDTO) {
//        Optional<User> userOptional = userRepository.findById(chatRequestDTO.getUserId());
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            Chat chat = new Chat(user, chatRequestDTO.getMessage());
//            return chatRepository.save(chat); // 채팅 저장
//        }
//        return null; // 사용자 없음
//    }

    // 퀘스트 진행 현황에 따른 메시지
    public String getPersonalizedMessage(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            // 여기에서 사용자 퀘스트 진행 상태를 조회하여 personalized 메시지를 생성하는 로직을 구현합니다.
            return "사용자의 퀘스트 진행 상태에 따른 메시지"; // 예시 메시지 반환
        }
        return "사용자를 찾을 수 없습니다."; // 사용자 없음
    }

    public String getMessage(ChatRequestDTO chatRequestDTO, Principal principal) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String param = objectMapper.writeValueAsString(chatRequestDTO);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(param, headers);

        String url = "https://ai-iyzk.onrender.com/chat";

        return uniToKor(restTemplate.postForObject(url, entity, String.class));
    }

    public String uniToKor(String uni){
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < uni.length(); i++){
            if(uni.charAt(i) == '\\' && uni.charAt(i+1) == 'u'){
                Character c = (char)Integer.parseInt(uni.substring(i+2, i+6), 16);
                result.append(c);
                i+=5;
            }
            else{
                result.append(uni.charAt(i));
            }
        }
        return result.toString();
    }
}

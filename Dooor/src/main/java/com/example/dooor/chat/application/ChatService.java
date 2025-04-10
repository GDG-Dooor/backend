package com.example.dooor.chat.application;

import com.example.dooor.user.domain.User;
import com.example.dooor.chat.api.request.ChatRequestDTO;
import com.example.dooor.user.domain.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

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

        System.out.println(chatRequestDTO.getMessage());
        User user = userRepository.findById(Integer.parseInt(principal.getName()))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        String userName = user.getName();

        String jsonBody = "{\"user_id\":\"" + principal.getName() + "\", \"user_name\":\"" + user.getName() + "\",\"message\":\"" + chatRequestDTO.getMessage() + "\"}";
//        String param = objectMapper.writeValueAsString(chatRequestDTO);
//        String param = objectMapper.writeValueAsString(jsonBody);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
//        HttpEntity<String> entity = new HttpEntity<>(param, headers);

        String url = "http://3.39.97.107:5000/chat";

        return uniToKor(restTemplate.postForObject(url, entity, String.class));
    }

    private String uniToKor(String uni){
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

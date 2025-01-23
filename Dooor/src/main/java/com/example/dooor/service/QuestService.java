package com.example.dooor.service;

import com.example.dooor.domain.QuestManagement.Quest;
import com.example.dooor.domain.QuestManagement.UserQuest;
import com.example.dooor.domain.User;

import com.example.dooor.dto.Quest.QuestDTO;
import com.example.dooor.repository.QuestRepository;
import com.example.dooor.repository.UserQuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestService {

    private final QuestRepository questRepository;
    private final UserQuestRepository userQuestRepository; // UserQuestRepository 추가

    // 퀘스트 목록 조회
    public List<Quest> getAllQuests() {
        return questRepository.findAll();
    }

    // 특정 퀘스트 정보 조회
    public Optional<Quest> getQuestById(Integer questId) {
        return questRepository.findById(questId);
    }

    // 퀘스트 시작
    public UserQuest startQuest(Integer userId, QuestDTO questDTO) {
        // 새로운 퀘스트 생성
        Quest quest = new Quest();
        quest.setTitle(questDTO.getTitle());
        quest.setDescription(questDTO.getDescription());
        quest.setCurrentStage(1); // 시작 단계
        quest.setStatus("진행 중"); // 상태 설정
        quest = questRepository.save(quest); // 퀘스트 저장

        // 사용자-퀘스트 관계 생성
        UserQuest userQuest = new UserQuest();
        userQuest.setUser(new User(userId)); // 사용자 설정
        userQuest.setQuest(quest); // 퀘스트 설정
        userQuest.setStatus("미완료"); // 초기 상태
        userQuestRepository.save(userQuest); // 사용자-퀘스트 관계 저장

        return userQuest; // 사용자-퀘스트 반환
    }

    // 퀘스트 완료 처리
    public boolean completeQuest(Integer userQuestId) {
        Optional<UserQuest> optionalUserQuest = userQuestRepository.findById(userQuestId);
        if (optionalUserQuest.isPresent()) {
            UserQuest userQuest = optionalUserQuest.get();
            userQuest.setStatus("클리어"); // 상태 변경
            userQuestRepository.save(userQuest);
            return true; // 완료 처리 성공
        }
        return false; // 사용자-퀘스트 없음
    }

    // 이미지 검증
    public boolean verifyImage(Integer userQuestId, String imageUrl) {
        Optional<UserQuest> optionalUserQuest = userQuestRepository.findById(userQuestId);
        if (optionalUserQuest.isPresent()) {
            UserQuest userQuest = optionalUserQuest.get();
            userQuest.setPhoto(imageUrl); // 인증 사진 경로 설정
            userQuest.setVerified(true); // 인증 여부 변경
            userQuestRepository.save(userQuest);
            return true; // 이미지 검증 성공
        }
        return false; // 사용자-퀘스트 없음
    }
}

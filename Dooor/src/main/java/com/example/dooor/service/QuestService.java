package com.example.dooor.service;

import com.example.dooor.domain.QuestManagement.Quest;
import com.example.dooor.domain.QuestManagement.Stage;
import com.example.dooor.domain.User;

import com.example.dooor.dto.Quest.QuestReq;
import com.example.dooor.dto.Quest.QuestRes;
import com.example.dooor.dto.Quest.UserQuestMapping;
import com.example.dooor.repository.QuestRepository;
import com.example.dooor.repository.StageRepository;
import com.example.dooor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestService {

    private final QuestRepository questRepository;
    private final StageRepository stageRepository;
    private final UserRepository userRepository;

    public QuestRes mkQuest(QuestReq questReq) {
        // 새로운 퀘스트 생성
        Stage stage = stageRepository.findById(questReq.getStageId())
                .orElseThrow(() -> new IllegalArgumentException("Stage not found"));

        Quest quest = Quest.builder()
                .questId(questReq.getQuestId())
                .title(questReq.getTitle())
                .description(questReq.getDescription())
                .stage(stage)
                .needImage(questReq.isNeedImage())
                .build();
        questRepository.save(quest); // 퀘스트 저장

        return QuestRes.builder()
                .questId(questReq.getQuestId())
                .title(questReq.getTitle())
                .description(questReq.getDescription())
                .stageId(questReq.getStageId())
                .needImage(questReq.isNeedImage())
                .build();
    }

    // 퀘스트 목록 조회
    public List<QuestRes> getAllQuests() {
        List<Quest> quests = questRepository.findAll();
        return quests.stream()
                .map(quest -> QuestRes.builder()
                        .questId(quest.getQuestId())
                        .title(quest.getTitle())
                        .description(quest.getDescription())
                        .stageId(quest.getStage().getId())
                        .needImage(quest.isNeedImage())
                        .build())
                .toList();
    }

    // 특정 퀘스트 정보 조회
    public QuestRes getQuestById(Integer questId) {
        Quest quest = questRepository.findById(questId).orElseThrow(() -> new IllegalArgumentException("Quest not found"));
        return QuestRes.builder()
                .questId(quest.getQuestId())
                .title(quest.getTitle())
                .description(quest.getDescription())
                .stageId(quest.getStage().getId())
                .needImage(quest.isNeedImage())
                .build();
    }
    
    // 퀘스트 시작
    public UserQuestMapping startQuest(Integer userId, Integer questId) {
        Quest quest = questRepository.findById(questId).orElseThrow(() -> new IllegalArgumentException("Quest not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if(questId <= user.getCurrentQuestId()) throw new IllegalArgumentException("이미 진행한 퀘스트입니다.");
        user.updateQuest(questId, false);
        return UserQuestMapping.builder()
                .userId(userId)
                .questId(questId)
                .needImage(quest.isNeedImage())
                .build();
    }

    // 퀘스트 완료 처리
    public boolean completeQuest(Integer userId, Integer questId) {
        if(0 == 0) { // 조건 수정
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
            user.updateQuest(questId, true);
            return true;
        }
        return false;
    }

//    public boolean validateQuest(MultipartFile multipartFile) {}

    public QuestRes updateQuest(QuestReq questReq) {
        Quest quest = questRepository.findById(questReq.getStageId()).orElseThrow(() -> new IllegalArgumentException("Quest not found"));
        Stage stage = stageRepository.findById(questReq.getStageId()).orElseThrow(() -> new IllegalArgumentException("Stage not found"));

        questRepository.save(Quest.builder()
                .questId(questReq.getQuestId())
                .title(questReq.getTitle())
                .description(questReq.getDescription())
                .stage(stage)
                .needImage(questReq.isNeedImage())
                .build());
        return QuestRes.builder()
                .questId(questReq.getQuestId())
                .title(questReq.getTitle())
                .description(questReq.getDescription())
                .stageId(questReq.getStageId())
                .needImage(questReq.isNeedImage())
                .build();
    }

    public void deleteQuestById(Integer questId) {
        questRepository.deleteById(questId);
    }

//    // 퀘스트 시작
//    public UserQuest startQuest(Integer userId, QuestRes questRes) {
//
//        // 사용자-퀘스트 관계 생성
//        UserQuest userQuest = new UserQuest();
//        userQuest.setUser(new User(userId)); // 사용자 설정
//        userQuest.setQuest(quest); // 퀘스트 설정
//        userQuest.setStatus("미완료"); // 초기 상태
//        userQuestRepository.save(userQuest); // 사용자-퀘스트 관계 저장
//
//        return userQuest; // 사용자-퀘스트 반환
//    }
//
//    // 퀘스트 완료 처리
//    public boolean completeQuest(Integer userQuestId) {
//        Optional<UserQuest> optionalUserQuest = userQuestRepository.findById(userQuestId);
//        if (optionalUserQuest.isPresent()) {
//            UserQuest userQuest = optionalUserQuest.get();
//            userQuest.setStatus("클리어"); // 상태 변경
//            userQuestRepository.save(userQuest);
//            return true; // 완료 처리 성공
//        }
//        return false; // 사용자-퀘스트 없음
//    }
//
//    // 이미지 검증
//    public boolean verifyImage(Integer userQuestId, String imageUrl) {
//        Optional<UserQuest> optionalUserQuest = userQuestRepository.findById(userQuestId);
//        if (optionalUserQuest.isPresent()) {
//            UserQuest userQuest = optionalUserQuest.get();
//            userQuest.setPhoto(imageUrl); // 인증 사진 경로 설정
//            userQuest.setVerified(true); // 인증 여부 변경
//            userQuestRepository.save(userQuest);
//            return true; // 이미지 검증 성공
//        }
//        return false; // 사용자-퀘스트 없음
//    }
}

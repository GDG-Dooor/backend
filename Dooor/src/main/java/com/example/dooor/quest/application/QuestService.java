package com.example.dooor.quest.application;

import com.example.dooor.quest.domain.Quest;
import com.example.dooor.ranking.application.RankingService;
import com.example.dooor.ranking.application.RedisRankingService;
import com.example.dooor.ranking.domain.Ranking;
import com.example.dooor.ranking.domain.repository.RankingRepository;
import com.example.dooor.stage.domain.Stage;
import com.example.dooor.service.AwsS3Service;
import com.example.dooor.user.domain.User;

import com.example.dooor.aws.AwsS3DTO;
import com.example.dooor.quest.api.dto.request.QuestReq;
import com.example.dooor.quest.api.dto.response.QuestRes;
import com.example.dooor.quest.api.dto.response.UserQuestMapping;
import com.example.dooor.quest.domain.repository.QuestRepository;
import com.example.dooor.stage.domain.repository.StageRepository;
import com.example.dooor.user.domain.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestService {

    private final QuestRepository questRepository;
    private final StageRepository stageRepository;
    private final UserRepository userRepository;
    private final AwsS3Service awsS3Service;
    private final ObjectMapper objectMapper;
    private final RankingRepository rankingRepository;
    private final RankingService rankingService;
    private final RedisRankingService redisRankingService;

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
        user.updateQuest(questId, false);
        userRepository.save(user);
        return UserQuestMapping.builder()
                .userId(userId)
                .questId(questId)
                .needImage(quest.isNeedImage())
                .build();
    }

    // 퀘스트 완료 처리
    public boolean completeQuest(Integer userId, Integer questId) {
        // 퀘스트가 실제로 완료되었는지 확인하는 조건을 여기에 추가합니다.
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 퀘스트 완료 로직
        user.updateQuest(questId, true);
        userRepository.save(user);

        // 랭킹 업데이트
        updateRanking(user); // 랭킹 업데이트 메서드 호출

        // 레디스에 점수 업데이트
        int addedScore = 10; // 고정 점수
        redisRankingService.updateRanking(userId, addedScore); // 레디스에 점수 업데이트

        return true; // 완료 처리 성공
    }

    private void updateRanking(User user) {
        // 사용자 ID로 랭킹 정보 조회
        Ranking ranking = rankingRepository.findByUser_UserId(user.getUserId());

        if (ranking == null) {
            // 사용자의 랭킹 정보가 없으면 새로 생성
            ranking = new Ranking(user, 0, null); // 기본 점수 0으로 초기화
            ranking.setRank(1); // 기본 랭킹 설정 (1위로 초기화)
        } else {
            // 기존의 랭킹 정보가 있으면 점수 증가
            ranking.setScore(ranking.getScore() + 10); // 예: 10점 추가
        }

        ranking.setUpdatedAt(LocalDateTime.now()); // 업데이트 시간 설정

        rankingRepository.save(ranking); // 랭킹 정보 저장
    }

    public Boolean validateQuest(MultipartFile multipartFile, Principal principal) throws IOException, ParseException {

        AwsS3DTO awsS3DTO = awsS3Service.uploadFile(multipartFile); // 사진 업로드

        RestTemplate restTemplate = new RestTemplate();

        Integer tokenUserId = Integer.parseInt(principal.getName()); // 토큰으로 접근한 유저 확인
        User user = userRepository.findById(tokenUserId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Integer currentQuestId = user.getCurrentQuestId();

        MultiValueMap<String, Object> image = new LinkedMultiValueMap<>();
        ByteArrayResource byteArrayResource = new ByteArrayResource(multipartFile.getBytes()){
            @Override
            public String getFilename(){
                return multipartFile.getOriginalFilename();
            }
        };
        image.add("image", byteArrayResource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<?> entity = new HttpEntity<>(image, headers);
//        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
//        HttpEntity<MultipartFile> entity = new HttpEntity<>(image, headers);

        if(currentQuestId == 10) {
            String url = "http://3.39.97.107:5000/positive";
            String st = uniToKor(restTemplate.postForObject(url, entity, String.class));
            st = st.replace("\\n", "\n");
            JSONObject jsonObj = parseJson(st);

            return Boolean.parseBoolean(jsonObj.get("is_writing_motivation").toString());
        }

        if(currentQuestId == 23) {
            String url = "http://3.39.97.107:5000/egg";
            String st = uniToKor(restTemplate.postForObject(url, entity, String.class));
            st = st.replace("\\n", "\n");
            JSONObject jsonObj = parseJson(st);

            return Boolean.parseBoolean(jsonObj.get("is_fried_egg").toString());
        }

        if(currentQuestId == 31) {
            String url = "http://3.39.97.107:5000/paper";
            String st = uniToKor(restTemplate.postForObject(url, entity, String.class));
            st = st.replace("\\n", "\n");
            JSONObject jsonObj = parseJson(st);

            return Boolean.parseBoolean(jsonObj.get("paper_detected").toString());
        }

        if(currentQuestId == 36) {
            String url = "http://3.39.97.107:5000/sky";
            String st = uniToKor(restTemplate.postForObject(url, entity, String.class));
            st = st.replace("\\n", "\n");
            JSONObject jsonObj = parseJson(st);

            return Boolean.parseBoolean(jsonObj.get("is_sky").toString());
        }

        if(currentQuestId == 40 || currentQuestId == 45 || currentQuestId == 47) {
            String url = "http://3.39.97.107:5000/ocr";
            String st = uniToKor(restTemplate.postForObject(url, entity, String.class));
            st = st.replace("\\n", "\n");
            JSONObject jsonObj = parseJson(st);

            return Boolean.parseBoolean(jsonObj.get("is_receipt").toString());
        }

        if(currentQuestId == 50) {
            String url = "http://3.39.97.107:5000/library";
            String st = uniToKor(restTemplate.postForObject(url, entity, String.class));
            st = st.replace("\\n", "\n");
            JSONObject jsonObj = parseJson(st);

            return Boolean.parseBoolean(jsonObj.get("is_library_receipt").toString());
        }

        if(currentQuestId == 52) {
            String url = "http://3.39.97.107:5000/microphone";
            String st = uniToKor(restTemplate.postForObject(url, entity, String.class));
            st = st.replace("\\n", "\n");
            JSONObject jsonObj = parseJson(st);

            return Boolean.parseBoolean(jsonObj.get("microphone_detected").toString());
        }

        if(currentQuestId == 53) {
            String url = "http://3.39.97.107:5000/movie";
            String st = uniToKor(restTemplate.postForObject(url, entity, String.class));
            st = st.replace("\\n", "\n");
            JSONObject jsonObj = parseJson(st);

            return Boolean.parseBoolean(jsonObj.get("is_movie_ticket").toString());
        }

        if(currentQuestId == 54) {
            String url = "http://3.39.97.107:5000/mountain";
            String st = uniToKor(restTemplate.postForObject(url, entity, String.class));
            st = st.replace("\\n", "\n");
            JSONObject jsonObj = parseJson(st);

            return Boolean.parseBoolean(jsonObj.get("is_mountain").toString());
        }

        return false;
//        return ResponseEntity.ok().build();
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

    private JSONObject parseJson(String st) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(st);
        return jsonObject;
    }

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

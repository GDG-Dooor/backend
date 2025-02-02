package com.example.dooor.service;

import com.example.dooor.domain.Role;
import com.example.dooor.domain.User;
import com.example.dooor.dto.User.UserSignUpDTO;
import com.example.dooor.dto.User.UserProfileDTO;
import com.example.dooor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 BCryptPasswordEncoder

    // 회원가입
    public UserProfileDTO signup(UserSignUpDTO userSignUpDTO) {
        if(userRepository.findByEmail(userSignUpDTO.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        User user = User.builder()
                .name(userSignUpDTO.getName())
                .email(userSignUpDTO.getEmail())
                .password(passwordEncoder.encode(userSignUpDTO.getPassword()))
                .role(Role.USER)
                .gender(userSignUpDTO.getGender())
                .build();
        userRepository.save(user);

        return UserProfileDTO.builder()
                .name(userSignUpDTO.getName())
                .email(userSignUpDTO.getEmail())
                .gender(userSignUpDTO.getGender())
                .rank(user.getRank())
                .build();
    }

    // 로그인
    public Optional<UserProfileDTO> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserProfileDTO userProfileDTO = UserProfileDTO.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .gender(user.getGender())
                    .rank(user.getRank())
                    .build();
            return Optional.of(userProfileDTO);
        }
        return Optional.empty(); // 로그인 실패
    }

    // 아이디 반환
    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    // 비밀번호 변경
    public boolean changePassword(Integer userId, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.changePassword(passwordEncoder.encode(newPassword)); // 비밀번호 암호화
            userRepository.save(user);
            return true; // 비밀번호 변경 성공
        }
        return false; // 사용자 없음
    }

    // 아이디 중복 체크
    public boolean checkIdExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // 사용자 퀘스트 진행 상태 조회
//    public String getUserProgress(Integer userId) {
//        // 여기에 사용자 퀘스트 진행 상태를 조회하는 로직 추가
//        return "사용자 퀘스트 진행 상태"; // 예시 응답
//    }

    // 로그아웃 처리 (세션 무효화 등 처리 필요)
    public void logout() {
        // 로그아웃 로직 (예: 세션 무효화)
        // 이 메서드는 실제로 Controller에서 세션을 무효화하는 방식으로 구현해야 함
    }

    // 탈퇴하기
    public boolean deleteUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true; // 탈퇴 성공
        }
        return false; // 사용자 없음
    }

    // 닉네임 변경
    public boolean updateName(Integer userId, String newName) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.changeName(newName);
            userRepository.save(user);
            return true; // 이름 변경 성공
        }
        return false; // 사용자 없음
    }
}

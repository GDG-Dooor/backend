package com.example.dooor.user.application;

import com.example.dooor.jwt.domain.RefreshToken;
import com.example.dooor.user.domain.Role;
import com.example.dooor.user.domain.User;
import com.example.dooor.jwt.api.dto.response.TokenDTO;
import com.example.dooor.user.api.dto.request.UserSignUpDTO;
import com.example.dooor.user.api.dto.response.UserProfileDTO;
import com.example.dooor.jwt.application.TokenProvider;
import com.example.dooor.jwt.domain.repository.RefreshTokenRepository;
import com.example.dooor.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 BCryptPasswordEncoder
    private final TokenProvider tokenProvider;

    // 회원가입
    public UserProfileDTO signup(UserSignUpDTO userSignUpDTO) {
        if(userRepository.findByEmail(userSignUpDTO.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
                .name(userSignUpDTO.getName())
                .email(userSignUpDTO.getEmail())
                .password(passwordEncoder.encode(userSignUpDTO.getPassword()))
                .role(Role.ROLE_USER)
                .currentQuestId(1)
                .currentQuestCleared(false)
//                .gender(userSignUpDTO.getGender())
                .build();
        userRepository.save(user);

        String refreshToken = tokenProvider.createRefreshToken(user);
        RefreshToken refBuilder = RefreshToken.builder()
                .id(user.getUserId())
                .refreshToken(refreshToken)
                .build();
        refreshTokenRepository.save(refBuilder);

        return UserProfileDTO.builder()
                .name(userSignUpDTO.getName())
                .email(userSignUpDTO.getEmail())
//                .gender(userSignUpDTO.getGender())
                .build();
    }

    // 로그인
    public TokenDTO login(String email, String password) { // 수정한 부분: 반환 타입을 TokenDTO로 변경
        Optional<User> userOptional = userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String accessToken = tokenProvider.createAccessToken(user); // 수정한 부분: 토큰 생성
            String refreshToken = tokenProvider.createRefreshToken(user);
            RefreshToken refBuilder = RefreshToken.builder()
                    .id(user.getUserId())
                    .refreshToken(refreshToken)
                    .build();
            refreshTokenRepository.save(refBuilder);

            System.out.println("1");
            return TokenDTO.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build(); // 수정한 부분: TokenDTO 반환
        }
        throw new RuntimeException("로그인 실패: 이메일 또는 비밀번호가 잘못되었습니다."); // 수정한 부분: 예외 처리
    }

    // 아이디 반환
    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    // 이메일 반환
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 비밀번호 변경
    public Integer changePassword(String email, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                return 1; // 비밀번호 일치로 변경 불가
            }
            user.changePassword(passwordEncoder.encode(newPassword)); // 비밀번호 암호화
            userRepository.save(user);
            return 0; // 비밀번호 변경 성공
        }
        return 2; // 사용자 없음
    }

//    // 비밀번호 변경
//    public Integer changePassword(Integer userId, String newPassword, Principal principal) {
//        Optional<User> userOptional = userRepository.findById(userId);
//        Integer tokenUserId = Integer.parseInt(principal.getName()); // 토큰으로 접근한 유저 확인
//        Optional<User> tokenUserOptional = userRepository.findById(tokenUserId);
//        if (userOptional.isPresent() && tokenUserOptional.isPresent()) {
//            User user = userOptional.get();
//            User tokenUser = tokenUserOptional.get();
//            if (passwordEncoder.matches(newPassword, user.getPassword())) {
//                return 1; // 비밀번호 일치로 변경 불가
//            }
//            if (!Objects.equals(user.getUserId(), tokenUser.getUserId())) {
//                return 2; // 토큰과 사용자 불일치로 변경 불가
//            }
//            user.changePassword(passwordEncoder.encode(newPassword)); // 비밀번호 암호화
//            userRepository.save(user);
//            return 0; // 비밀번호 변경 성공
//        }
//        return 3; // 사용자 없음
//    }

    // 아이디 중복 체크
    public boolean checkIdExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // 비밀번호 일치 확인
    public boolean checkPassword(String password, String passwordConfirmation) {
        return password.equals(passwordConfirmation);
    }

    // 사용자 퀘스트 진행 상태 조회
//    public String getUserProgress(Integer userId) {
//        // 여기에 사용자 퀘스트 진행 상태를 조회하는 로직 추가
//        return "사용자 퀘스트 진행 상태"; // 예시 응답
//    }

    // 로그아웃 처리 (세션 무효화 등 처리 필요)
    public void logout(Principal principal) {
        // 사용자의 ID를 통해 리프레시 토큰을 찾아 무효화
        Integer userId = Integer.parseInt(principal.getName());

        // 리프레시 토큰 무효화 로직 추가
        refreshTokenRepository.findById(userId).ifPresent(refreshToken -> {
            refreshTokenRepository.delete(refreshToken); // 리프레시 토큰 삭제
        });

        // 엑세스 토큰 무효화 로직 (예: 블랙리스트에 추가)
        // 이 부분은 실제 구현에 따라 다를 수 있음
        System.out.println("사용자가 로그아웃했습니다: " + principal.getName());
    }

    // 탈퇴하기
    public Integer deleteUser(Integer userId, Principal principal) {
        Optional<User> userOptional = userRepository.findById(userId);
        Integer tokenUserId = Integer.parseInt(principal.getName()); // 토큰으로 접근한 유저 확인
        Optional<User> tokenUserOptional = userRepository.findById(tokenUserId);
        if (userOptional.isPresent() && tokenUserOptional.isPresent()) {
            User user = userOptional.get();
            User tokenUser = tokenUserOptional.get();
            if (!Objects.equals(user.getUserId(), tokenUser.getUserId())) {
                return 1; // 토큰과 사용자 불일치로 변경 불가
            }
            userRepository.delete(user);
            return 0; // 탈퇴 성공
        }
        return 2; // 사용자 없음
    }

    // 닉네임 변경
    public Integer updateName(Integer userId, String newName, Principal principal) {
        Optional<User> userOptional = userRepository.findById(userId);
        Integer tokenUserId = Integer.parseInt(principal.getName()); // 토큰으로 접근한 유저 확인
        Optional<User> tokenUserOptional = userRepository.findById(tokenUserId);
        if (userOptional.isPresent() && tokenUserOptional.isPresent()) {
            User user = userOptional.get();
            User tokenUser = tokenUserOptional.get();
            if (user.getName().equals(newName)) {
                return 1; // 이름 일치로 변경 불가
            }
            if (!Objects.equals(user.getUserId(), tokenUser.getUserId())) {
                return 2; // 토큰과 사용자 불일치로 변경 불가
            }
            user.changeName(newName);
            userRepository.save(user);
            return 0; // 이름 변경 성공
        }
        return 3; // 사용자 없음
    }
}

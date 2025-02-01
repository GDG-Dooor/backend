package com.example.dooor.controller;

import com.example.dooor.domain.User;
import com.example.dooor.dto.User.UserDTO;
import com.example.dooor.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody UserDTO userDTO) {
        User user = userService.signup(userDTO);
        return ResponseEntity.ok(user); // 사용자 정보 반환
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOptional = userService.login(email, password);
        return userOptional.map(ResponseEntity::ok) // 로그인 성공 시 사용자 정보 반환
                .orElseGet(() -> ResponseEntity.badRequest().build()); // 로그인 실패 시 400 반환
    }

    // 아이디 반환
    @GetMapping("/id")
    public ResponseEntity<User> getUserById(@RequestParam Integer userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        return userOptional.map(ResponseEntity::ok) // 사용자 존재 시 반환
                .orElseGet(() -> ResponseEntity.notFound().build()); // 사용자 없음
    }

    // 비밀번호 변경
    @PostMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestParam Integer userId, @RequestParam String newPassword) {
        boolean changed = userService.changePassword(userId, newPassword);
        return changed ? ResponseEntity.ok().build() // 비밀번호 변경 성공
                : ResponseEntity.notFound().build(); // 사용자 없음
    }

    // 아이디 중복 체크
    @GetMapping("/check-id")
    public ResponseEntity<Boolean> checkIdExists(@RequestParam String email) {
        boolean exists = userService.checkIdExists(email);
        return ResponseEntity.ok(exists); // 중복 여부 반환
    }

    // 사용자 퀘스트 진행 상태 조회
    @GetMapping("/progress")
    public ResponseEntity<String> getUserProgress(@RequestParam Integer userId) {
        String progress = userService.getUserProgress(userId);
        return ResponseEntity.ok(progress); // 진행 상태 반환
    }

    // 로그아웃 처리
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        userService.logout(); // 로그아웃 처리
        return ResponseEntity.ok().build(); // 성공적으로 로그아웃
    }

    // 탈퇴하기
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam Integer userId) {
        boolean deleted = userService.deleteUser(userId);
        return deleted ? ResponseEntity.ok().build() // 탈퇴 성공
                : ResponseEntity.notFound().build(); // 사용자 없음
    }

    // 닉네임 변경
    @PatchMapping("/nickname")
    public ResponseEntity<Void> updateNickname(@RequestParam Integer userId, @RequestParam String newNickname) {
        boolean updated = userService.updateNickname(userId, newNickname);
        return updated ? ResponseEntity.ok().build() // 닉네임 변경 성공
                : ResponseEntity.notFound().build(); // 사용자 없음
    }
}

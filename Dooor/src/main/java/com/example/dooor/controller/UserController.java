package com.example.dooor.controller;

import com.example.dooor.domain.User;
import com.example.dooor.dto.TokenDTO;
import com.example.dooor.dto.User.UserSignUpDTO;
import com.example.dooor.dto.User.UserProfileDTO;
import com.example.dooor.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 API")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "회원가입 실패")
    })
    @Parameters({
            @Parameter(name = "name", description = "이름", example = "홍길동"),
            @Parameter(name = "email", description = "이메일(중복X)", example = "example@gmail.com"),
            @Parameter(name = "password", description = "암호 형식 8~12자", example = "abcd1234"),
//            @Parameter(name = "gender", description = "성별", example = "남성/여성")
    })
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignUpDTO userSignUpDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                String message = fieldError.getDefaultMessage();
                sb.append(fieldError.getField()).append(": ").append(message).append("\n");
            });
            return ResponseEntity.badRequest().body(sb.toString());
        }

        UserProfileDTO user = userService.signup(userSignUpDTO);
        return ResponseEntity.ok(user); // 사용자 정보 반환
    }

    // 로그인
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자가 로그인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "로그인 실패")
    })
    @Parameters({
            @Parameter(name = "email", description = "이메일", example = "example@gmail.com"),
            @Parameter(name = "password", description = "암호 형식 8~12자", example = "abcd1234")
    })
    public ResponseEntity<TokenDTO> login(@RequestParam String email, @RequestParam String password) {
        try {
            TokenDTO token = userService.login(email, password); // 로그인 성공 시 토큰 반환
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 로그인 실패 시 400 반환
        }
    }

    // 아이디 반환
    @GetMapping("/id")
    @Operation(summary = "사용자 ID 조회", description = "주어진 사용자 ID에 대한 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보를 성공적으로 반환했습니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    public ResponseEntity<User> getUserById(@RequestParam Integer userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        return userOptional.map(ResponseEntity::ok) // 사용자 존재 시 반환
                .orElseGet(() -> ResponseEntity.notFound().build()); // 사용자 없음
    }

    @GetMapping("/email")
    @Operation(summary = "사용자 이메일 조회", description = "주어진 사용자 이메일에 대한 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보를 성공적으로 반환했습니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        Optional<User> userOptional = userService.getUserByEmail(email);
        return userOptional.map(ResponseEntity::ok) // 사용자 존재 시 반환
                .orElseGet(() -> ResponseEntity.notFound().build()); // 사용자 없음
    }

    // 아이디 중복 체크
    @GetMapping("/check-id")
    @Operation(summary = "아이디 중복 체크", description = "회원가입 시 주어진 이메일이 이미 존재하는지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "중복 여부 반환"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    public ResponseEntity<Boolean> checkIdExists(@RequestParam String email) {
        boolean exists = userService.checkIdExists(email);
        return ResponseEntity.ok(exists); // 중복 여부 반환
    }

    @GetMapping("/check-pw")
    @Operation(summary = "암호 중복체크", description = "회원가입 시 암호의 일치를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일치 여부 반환"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    public ResponseEntity<Boolean> checkPw(@RequestParam String password, @RequestParam String passwordConfirm) {
        return ResponseEntity.ok(userService.checkPassword(password, passwordConfirm));
    }

    // 로그아웃 처리
    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "사용자가 로그아웃합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 로그아웃했습니다.")
    })
    public ResponseEntity<Void> logout() {
        userService.logout(); // 로그아웃 처리
        return ResponseEntity.ok().build(); // 성공적으로 로그아웃
    }

    // 탈퇴하기
    @DeleteMapping
    @Operation(summary = "사용자 탈퇴", description = "사용자가 탈퇴합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "탈퇴 성공"),
            @ApiResponse(responseCode = "403", description = "탈퇴할 수 있는 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    public ResponseEntity<String> deleteUser(@RequestParam Integer userId, Principal principal) {
        Integer deleted = userService.deleteUser(userId, principal);
        if (deleted == 0) return ResponseEntity.ok().build();
        else if (deleted == 1) return ResponseEntity.status(403)
                .body("탈퇴할 수 있는 권한이 없습니다.");
        else return ResponseEntity.notFound().build();
    }

    // 비밀번호 변경
    @PostMapping("/password")
    @Operation(summary = "비밀번호 변경", description = "사용자가 비밀번호를 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
            @ApiResponse(responseCode = "403", description = "비밀번호 변경 실패: 권한 없음 또는 현재 비밀번호와 동일"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    public ResponseEntity<String> changePassword(@RequestParam Integer userId, @RequestParam String newPassword, Principal principal) {
        Integer changed = userService.changePassword(userId, newPassword, principal);
        if (changed == 0) return ResponseEntity.ok().build();
        else if (changed == 1) return ResponseEntity.status(403)
                .body("현재 사용중인 비밀번호입니다.");
        else if (changed == 2) return ResponseEntity.status(403)
                .body("변경할 수 있는 권한이 없습니다.");
        else return ResponseEntity.notFound().build();
    }

    // 닉네임 변경
    @PatchMapping("/name")
    @Operation(summary = "닉네임 변경", description = "사용자가 닉네임을 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 변경 성공"),
            @ApiResponse(responseCode = "403", description = "닉네임 변경 실패: 현재 사용중인 이름 또는 권한 없음"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    public ResponseEntity<String> updateName(@RequestParam Integer userId, @RequestParam String newName, Principal principal) {
        Integer updated = userService.updateName(userId, newName, principal);
        if (updated == 0) return ResponseEntity.ok().build();
        else if (updated == 1) return ResponseEntity.status(403)
                .body("현재 사용중인 이름입니다.");
        else if (updated == 2) return ResponseEntity.status(403)
                .body("변경할 수 있는 권한이 없습니다.");
        else return ResponseEntity.notFound().build();
    }
}

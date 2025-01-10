package com.example.dooor.repository;

import com.example.dooor.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email); // 이메일로 사용자 검색
    Optional<User> findByGoogleId(String googleId); // 구글 ID로 사용자 검색
    Optional<User> findByPhoneNumber(String phoneNumber);
}

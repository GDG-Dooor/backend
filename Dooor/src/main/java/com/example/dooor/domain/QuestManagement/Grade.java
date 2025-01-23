package com.example.dooor.domain.QuestManagement;

import com.example.dooor.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gradeId; // 등급 고유 ID

    @Column(name = "grade_name", nullable = false, length = 50)
    private String gradeName; // 등급 이름

    @Column(name = "required_stage", nullable = false)
    private Integer requiredStage; // 필요한 단계 수

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // 등급 설명

    @OneToMany(mappedBy = "currentGrade")
    private List<User> users; // 해당 등급을 가진 사용자들

    // 생성자
    public Grade(String gradeName, Integer requiredStage, String description) {
        this.gradeName = gradeName;
        this.requiredStage = requiredStage;
        this.description = description;
    }
}

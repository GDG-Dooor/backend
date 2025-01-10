package com.example.dooor.domain.QuestManagement;

import com.example.dooor.domain.User;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Grade")
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

    // 기본 생성자
    public Grade() {}

    // 생성자
    public Grade(String gradeName, Integer requiredStage, String description) {
        this.gradeName = gradeName;
        this.requiredStage = requiredStage;
        this.description = description;
    }

    // Getter 및 Setter
    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getRequiredStage() {
        return requiredStage;
    }

    public void setRequiredStage(Integer requiredStage) {
        this.requiredStage = requiredStage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

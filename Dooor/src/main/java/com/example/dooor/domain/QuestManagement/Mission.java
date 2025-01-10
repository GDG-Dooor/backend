package com.example.dooor.domain.QuestManagement;

import jakarta.persistence.*;

@Entity
@Table(name = "Mission")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer missionId; // 미션 고유 ID

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stage; // 해당 미션이 속한 단계

    @Column(name = "mission_number", nullable = false)
    private Integer missionNumber; // 미션 번호

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // 미션 설명

    @Column(name = "status", columnDefinition = "ENUM('완료', '미완료') DEFAULT '미완료'")
    private String status; // 미션 상태

    // 기본 생성자
    public Mission() {}

    // 생성자
    public Mission(Stage stage, Integer missionNumber, String description, String status) {
        this.stage = stage;
        this.missionNumber = missionNumber;
        this.description = description;
        this.status = status != null ? status : "미완료";
    }

    // Getter 및 Setter
    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Integer getMissionNumber() {
        return missionNumber;
    }

    public void setMissionNumber(Integer missionNumber) {
        this.missionNumber = missionNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

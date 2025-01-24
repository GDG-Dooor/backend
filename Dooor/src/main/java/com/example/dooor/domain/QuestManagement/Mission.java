package com.example.dooor.domain.QuestManagement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    // 생성자
    public Mission(Stage stage, Integer missionNumber, String description, String status) {
        this.stage = stage;
        this.missionNumber = missionNumber;
        this.description = description;
        this.status = status != null ? status : "미완료";
    }
}

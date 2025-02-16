package com.example.dooor.dto.Stage;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StageReq {

    private Integer stageId;
    private String title;
    private String description;
}

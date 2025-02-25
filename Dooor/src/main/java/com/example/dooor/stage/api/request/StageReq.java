package com.example.dooor.stage.api.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StageReq {

    private Integer stageId;
    private String title;
    private String description;
}

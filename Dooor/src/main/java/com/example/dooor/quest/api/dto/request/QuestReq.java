package com.example.dooor.quest.api.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestReq {
    private Integer questId;
    private String title;
    private String description;
    private Integer stageId;
    private boolean needImage;
}

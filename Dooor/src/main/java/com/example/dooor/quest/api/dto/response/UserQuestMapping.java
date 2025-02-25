package com.example.dooor.quest.api.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserQuestMapping {
    private Integer userId;
    private Integer questId;
    private boolean needImage;
}

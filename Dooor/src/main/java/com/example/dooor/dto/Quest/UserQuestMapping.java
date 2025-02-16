package com.example.dooor.dto.Quest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserQuestMapping {
    private Integer userId;
    private Integer questId;
    private boolean needImage;
}

package com.EChowk.EChowk.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkillStatDto {
    private String skillName;
    private long requestCount;
    private long offerCount;
}

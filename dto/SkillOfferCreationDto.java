package com.EChowk.EChowk.dto;

import lombok.Data;

@Data
public class SkillOfferCreationDto {
    private String userId;
    private String skillId;
    private String title;
    private String description;
    private boolean available;
}

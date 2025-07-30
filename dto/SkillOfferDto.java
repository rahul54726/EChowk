package com.EChowk.EChowk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillOfferDto {
    private String id;
    private SkillDto skill;
    private UserDto user;
    private boolean available;
}

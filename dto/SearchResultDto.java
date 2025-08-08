package com.EChowk.EChowk.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDto {
    private List<SkillDto> skills;
    private List<SkillOfferDto> skillOffers;
}


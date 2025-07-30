package com.EChowk.EChowk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardStatsDto {
    private int totalSkills;
    private int totalOffers;
    private int totalRequests;
    private double averageRating;
}

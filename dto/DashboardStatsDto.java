package com.EChowk.EChowk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardStatsDto {
    private int totalSkills;
    private int totalOffers;
    private int totalRequests;
    private double averageRating;

    // Additional stats for comprehensive dashboard
    private int totalStudents;
    private int totalConnections;
    private int totalReviews;
    private int activeOffers;
    private int pendingRequests;
    private int completedSessions;

    // Graph data
    private List<GraphDataDto> monthlyStats;
    private List<GraphDataDto> skillDistribution;
    private List<GraphDataDto> ratingTrends;
    private Map<String, Integer> requestStatusDistribution;
    private Map<String, Integer> offerStatusDistribution;
}
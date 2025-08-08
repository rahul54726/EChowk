package com.EChowk.EChowk.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewSummaryDto {
    private String reviewerName;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
    private String skillTitle; // optional
}

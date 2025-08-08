package com.EChowk.EChowk.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ActivityDto {
    private String type; // e.g., "REQUEST", "REVIEW", "SESSION"
    private String title;
    private String description;
    private LocalDateTime createdAt;
}

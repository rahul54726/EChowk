package com.EChowk.EChowk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor // 👈 This generates a public constructor with all fields
@NoArgsConstructor
public class ReviewDto {
    private String id;
    private String reviewerId;
    private String reviewerName;
    private String skillOfferId;
    private int rating;
    private String comment;
    private String createdAt;
}

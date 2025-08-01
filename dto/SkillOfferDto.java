package com.EChowk.EChowk.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillOfferDto {
    private String id;
    private String title;
    private String description;

    private String skillId;
    private String skillName;

    private String userId;
    private String userName;
    private String userAvatar;

    private boolean availability;
    private int maxStudents;
    private int currentStudents;

    private String status;
    private String createdAt;

    private double averageRating;
    private  int numReviews;
}

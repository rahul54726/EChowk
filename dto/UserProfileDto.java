package com.EChowk.EChowk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDto {
    private String id;
    private String name;
    private String email;
    private String bio;
    private String location;
    private double averageRating;
    private String profilePictureUrl;
    private String role;
}

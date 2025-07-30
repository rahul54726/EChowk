package com.EChowk.EChowk.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDto {

    private String id;

    private SkillOfferDto skillOffer;

    private UserDto requester;

    private String status; // e.g., PENDING, ACCEPTED, REJECTED
}

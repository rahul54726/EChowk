package com.EChowk.EChowk.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCreationDto {
    private String skillOfferId;
    private String requesterId;
}

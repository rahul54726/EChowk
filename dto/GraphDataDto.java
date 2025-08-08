package com.EChowk.EChowk.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GraphDataDto {
    private List<String> labels; // e.g., dates
    private List<Long> offers;   // counts for each label
    private List<Long> requests; // counts for each label
}

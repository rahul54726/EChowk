package com.EChowk.EChowk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultPageDto<T> {
    private List<Object> content ;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}

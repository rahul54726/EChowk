package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Service.SearchService;
import com.EChowk.EChowk.dto.SearchResultPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public SearchResultPageDto<Object> unifiedSearch(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String dir) {
        return searchService.unifiedSearch(query, page, size, sort, dir);
    }

}

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
    public SearchResultPageDto search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return searchService.unifiedSearch(keyword, page, size);
    }
}

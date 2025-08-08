package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.dto.SearchResultPageDto;
import com.EChowk.EChowk.dto.SkillDto;
import com.EChowk.EChowk.dto.SkillOfferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SkillService skillService;
    private final SkillOfferService skillOfferService;

    public SearchResultPageDto unifiedSearch(String keyword, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);

        Page<SkillDto> skills = skillService.searchSkills(keyword, pageable);
        Page<SkillOfferDto> offers = skillOfferService.searchSkillOffers(keyword, pageable);

        // Combine both types of results into a single List<Object> or a common interface
        List<Object> combinedResults = new ArrayList<>();
        combinedResults.addAll(skills.getContent());
        combinedResults.addAll(offers.getContent());

        long totalElements = skills.getTotalElements() + offers.getTotalElements();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new SearchResultPageDto(combinedResults, page, size, totalElements, totalPages);
    }
}

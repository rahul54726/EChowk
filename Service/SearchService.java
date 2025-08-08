package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.dto.SearchResultPageDto;
import com.EChowk.EChowk.dto.SkillDto;
import com.EChowk.EChowk.dto.SkillOfferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SkillService skillService;
    private final SkillOfferService skillOfferService;

    public SearchResultPageDto unifiedSearch(String keyword, int page, int size, String sortField, String sortDir) {
        // For simplicity, fetch enough results from each so sorting can be done together
        PageRequest pageable = PageRequest.of(0, Integer.MAX_VALUE);

        Page<SkillDto> skills = skillService.searchSkills(keyword, pageable);
        Page<SkillOfferDto> offers = skillOfferService.searchSkillOffers(keyword, pageable);

        List<Object> combinedResults = new ArrayList<>();
        combinedResults.addAll(skills.getContent());
        combinedResults.addAll(offers.getContent());

        // Manual sorting based on field name and direction
        Comparator<Object> comparator = Comparator.comparing(obj -> {
            try {
                return (Comparable) obj.getClass().getMethod("get" + capitalize(sortField)).invoke(obj);
            } catch (Exception e) {
                return null; // If field not found, push nulls to end
            }
        }, Comparator.nullsLast(Comparator.naturalOrder()));

        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }

        combinedResults.sort(comparator);

        // Manual pagination after sorting
        int start = page * size;
        int end = Math.min(start + size, combinedResults.size());
        List<Object> pagedList = start < end ? combinedResults.subList(start, end) : new ArrayList<>();

        long totalElements = combinedResults.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new SearchResultPageDto(pagedList, page, size, totalElements, totalPages);
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Repository.SkillOfferRepo;
import com.EChowk.EChowk.Repository.SkillRepo;
import com.EChowk.EChowk.Repository.SkillRequestRepo;
import com.EChowk.EChowk.Repository.UserRepo;
import com.EChowk.EChowk.dto.DashboardStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final SkillRepo skillRepo;
    private final SkillOfferRepo skillOfferRepo;
    private final SkillRequestRepo skillRequestRepo;
    private final UserRepo userRepo;

    public DashboardStatsDto getUserStats(String userId){
        int totalSkills = skillRepo.findByUserId(userId).size();
        int totalOffers = skillOfferRepo.findByUserId(userId).size();
        int totalRequest = skillRequestRepo.findByUserId(userId).size();
        double rating = userRepo.findById(userId)
                .map(user -> user.getAverageRating() != null ? user.getAverageRating() : 0.0)
                .orElse(0.0);
        return DashboardStatsDto.builder().
                totalSkills(totalSkills)
                .totalOffers(totalOffers)
                .totalRequests(totalRequest)
                .averageRating(rating)
                .build();
    }
}

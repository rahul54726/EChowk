package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Repository.*;
import com.EChowk.EChowk.dto.DashboardStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final SkillOfferRepo skillOfferRepo;
    private final RequestRepo requestRepo;
    private final ReviewRepo reviewRepo;
    private final ConnectionRepo connectionRepo;

    public DashboardStatsDto getDashboardStats(String userId) {

        // Total skills (this should be skills the user can teach)
        int totalSkills = (int) skillOfferRepo.countByUser_Id(userId);

        // Total offers (active skill offers by user)
        int totalOffers = (int) skillOfferRepo.countByUser_IdAndStatus(userId, "OPEN");

        // Total requests (pending requests for user's offers)
        int totalRequests = (int) requestRepo.countBySkillOffer_User_IdAndStatusIn(
                userId,
                List.of("PENDING", "IN_PROGRESS")
        );

        // Average rating from reviews
        Double avgRating = reviewRepo.getAverageRatingForUser(userId);
        double averageRating = (avgRating != null) ? avgRating : 0.0;

        return DashboardStatsDto.builder()
                .totalSkills(totalSkills)
                .totalOffers(totalOffers)
                .totalRequests(totalRequests)
                .averageRating(averageRating)
                .build();
    }
}

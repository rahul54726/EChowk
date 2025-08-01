package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final SkillOfferRepo skillOfferRepo;
    private final RequestRepo requestRepo;
    private final ReviewRepo reviewRepo;
//    public DashboardStatsDto getUserStats(String userId) {
//        int totalSkills = skillRepo.countByUserId(userId);
//        int totalOffers = skillOfferRepo.findByUserId(userId).size();
//        int totalRequests = requestRepo.findByRequester_Id(userId).size();
//        double rating = userRepo.findById(userId)
//                .map(user -> user.getAverageRating() != null ? user.getAverageRating() : 0.0)
//                .orElse(0.0);
//
//        return DashboardStatsDto.builder()
//                .totalSkills(totalSkills)
//                .totalOffers(totalOffers)
//                .totalRequests(totalRequests)
//                .averageRating(rating)
//                .build();
//    }
public Map<String, Object> getUserStats(String userId) {
    long totalOffers = skillOfferRepo.countByUser_Id(userId);
    long totalRequests = requestRepo.countByRequester_Id(userId);
    long reviewsGiven = reviewRepo.countByReviewer_Id(userId);

    long acceptedRequests = requestRepo.findByRequester_Id(userId).stream()
            .filter(req -> "ACCEPTED".equalsIgnoreCase(req.getStatus()))
            .count();

    return Map.of(
            "totalOffers", totalOffers,
            "totalRequests", totalRequests,
            "acceptedRequests", acceptedRequests,
            "reviewsGiven", reviewsGiven
    );
}

}

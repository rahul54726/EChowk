package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Repository.*;
import com.EChowk.EChowk.enums.ConnectionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final SkillOfferRepo skillOfferRepo;
    private final RequestRepo requestRepo;
    private final ReviewRepo reviewRepo;
    private final ConnectionRepo connectionRepo;

    public Map<String, Object> getUserStats(String userId) {

        // Skills shared by user
        long skillsShared = skillOfferRepo.countByUser_Id(userId);

        // Active requests (pending or in progress)
        long activeRequests = requestRepo.countByRequester_IdAndStatusIn(
                userId,
                List.of("PENDING", "IN_PROGRESS")
        );

        // Total accepted connections
        long totalConnections = connectionRepo
                .countBySenderIdOrReceiverIdAndStatus(userId, userId, ConnectionStatus.ACCEPTED);

        // Average rating
        Double avgRating = reviewRepo.getAverageRatingForUser(userId);
        avgRating = (avgRating != null) ? avgRating : 0.0;

        return Map.of(
                "skillsShared", skillsShared,
                "connections", totalConnections,
                "activeRequests", activeRequests,
                "averageRating", avgRating
        );
    }
}

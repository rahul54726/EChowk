package com.EChowk.EChowk.Repository;

import com.EChowk.EChowk.Entity.Review;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepo extends MongoRepository<Review,String> {
    List<Review> findBySkillOffer_Id(String offerId);
    long countByReviewer_Id(String userId);
    void deleteByReviewerId(String id);
    void deleteBySkillOfferId(String skillOfferId);
    @Aggregation(pipeline = {
            "{ $match: { reviewedUserId: ?0 } }",
            "{ $group: { _id: null, avgRating: { $avg: '$rating' } } }"
    })
    Double getAverageRatingForUser(String userId);
}

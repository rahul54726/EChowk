package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.Review;
import com.EChowk.EChowk.Entity.Skill;
import com.EChowk.EChowk.Entity.SkillOffer;
import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.ReviewRepo;
import com.EChowk.EChowk.Repository.SkillOfferRepo;
import com.EChowk.EChowk.Repository.UserRepo;
import com.EChowk.EChowk.dto.ReviewDto;
import com.EChowk.EChowk.exception.ResourceNotFoundException;
import com.EChowk.EChowk.utils.DtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepo reviewRepo;
    private final UserRepo userRepo;
    private final SkillOfferRepo skillOfferRepo;
    public Review createReview(ReviewDto dto){
        User reviewer = userRepo.findById(dto.getReviewerId()).orElseThrow(() -> new ResourceNotFoundException("Reviewer Not Found"));
        SkillOffer offer = skillOfferRepo.findById(dto.getSkillOfferId()).orElseThrow(()->new ResourceNotFoundException("Skill offer not Found"));
        Review review = Review.builder()
                .reviewer(reviewer)
                .skillOffer(offer)
                .rating(dto.getRating())
                .content(dto.getComment())
                .createdAt(LocalDateTime.now())
                .build();
        reviewRepo.save(review);

        List<Review> allReviews = reviewRepo.findBySkillOffer_Id(offer.getId());
        double avg = allReviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
        offer.setAverageRating(avg);
        offer.setNumReviews(allReviews.size());
        skillOfferRepo.save(offer);
        return review;
    }
    public List<Review> getReviewsForOffer(String offerId){
        return reviewRepo.findBySkillOffer_Id(offerId);
    }
}

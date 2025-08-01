package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Entity.Review;
import com.EChowk.EChowk.Service.ReviewService;
import com.EChowk.EChowk.dto.ReviewDto;
import com.EChowk.EChowk.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewDto reviewDto){
        var createdReview = reviewService.createReview(reviewDto);
        return new ResponseEntity<>(DtoMapper.toReviewDto(createdReview), HttpStatus.OK);
    }
    @GetMapping("/offers/{offerId}")
    public ResponseEntity<?> getReviewsByOffer(@PathVariable String offerId){
       var reviews =  reviewService.getReviewsForOffer(offerId).stream().map(DtoMapper::toReviewDto).collect(Collectors.toList());
       return new ResponseEntity<>(reviews,HttpStatus.OK);
    }
    @GetMapping("/stats/{offersId}")
    public ResponseEntity<?> getReviewStats(@PathVariable String offerId){
        List<Review> reviews = reviewService.getReviewsForOffer(offerId);
        double avgRating = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
        int totalReviews = reviews.size();
        return  new ResponseEntity<>(Map.of("averageRating",avgRating,"totalReviews",totalReviews),HttpStatus.OK);
    }
}

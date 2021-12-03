package com.danielturato.reviewapi.controller;

import com.danielturato.reviewapi.model.Review;
import com.danielturato.reviewapi.service.ReviewService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewControllerImpl implements ReviewController {

    private final ReviewService reviewService;

    public ReviewControllerImpl(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public List<Review> getReviews(Optional<String> restaurantId, Optional<String> accountId) {
        return reviewService.getReviews(restaurantId, accountId);
    }
}

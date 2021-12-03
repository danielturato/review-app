package com.danielturato.reviewapi.service;

import com.danielturato.reviewapi.model.Review;
import com.danielturato.reviewapi.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getReviews(Optional<String> restaurantId, Optional<String> accountId) {
        if (!restaurantId.isPresent() && !accountId.isPresent()) {
            //TODO:drt - throw exc
            throw new RuntimeException("need params");
        }

        if (restaurantId.isPresent() && accountId.isPresent()) {
            return reviewRepository
                    .getReviewByRestaurantIdAndAccountId(restaurantId.get(), accountId.get());
        }

        if (restaurantId.isPresent()) {
            return reviewRepository
                    .getReviewByRestaurantId(restaurantId.get());
        }

        return reviewRepository.getReviewByAccountId(accountId.get());
    }
}

package com.danielturato.reviewapi.service;

import com.danielturato.reviewapi.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<Review> getReviews(Optional<String> restaurantId,
                            Optional<String> accountId);

}

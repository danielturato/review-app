package com.danielturato.reviewapi.controller;

import com.danielturato.reviewapi.model.Review;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface ReviewController {

    @GetMapping("/")
    List<Review> getReviews(@RequestParam Optional<String> restaurantId,
                            @RequestParam Optional<String> accountId);
}

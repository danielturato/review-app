package com.danielturato.reviewapi.repository;

import com.danielturato.reviewapi.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {

    @Query("{restaurant_id: '?0'}")
    List<Review> getReviewByRestaurantId(String restaurantId);

    @Query("{account_id '?0'}")
    List<Review> getReviewByAccountId(String accountId);

    @Query("{restaurant_id: '?0', account_id: '?1'}")
    List<Review> getReviewByRestaurantIdAndAccountId(String restaurantId, String accountId);

}

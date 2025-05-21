package com.realestate.agentsystem.service;

import com.realestate.agentsystem.model.Review;
import com.realestate.agentsystem.repository.ReviewFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
    private final ReviewFileRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewFileRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review saveReview(Review review) {
        if (review.getId() == null || review.getId().isEmpty()) {
            review.setId(UUID.randomUUID().toString());
        }

        if (review.getDateCreated() == null) {
            review.setDateCreated(LocalDateTime.now());
        }

        reviewRepository.saveReview(review);
        return review;
    }

    public Review getReviewById(String id) {
        return reviewRepository.findReviewById(id);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.getAllReviews();
    }

    public List<Review> getReviewsByAgentId(String agentId) {
        return reviewRepository.getReviewsByAgentId(agentId);
    }

    public boolean deleteReview(String id) {
        Review review = getReviewById(id);
        if (review != null) {
            List<Review> reviews = getAllReviews();
            reviews.removeIf(r -> r.getId().equals(id));
            reviewRepository.saveReviewsToFile(reviews);
            return true;
        }
        return false;
    }
}
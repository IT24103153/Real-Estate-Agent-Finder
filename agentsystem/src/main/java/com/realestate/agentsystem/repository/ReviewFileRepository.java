package com.realestate.agentsystem.repository;

import com.realestate.agentsystem.model.Review;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ReviewFileRepository {
    private static final String REVIEWS_FILE = "reviews.txt";

    public ReviewFileRepository() {
        // Initialize file if it doesn't exist
        try {
            File file = new File(REVIEWS_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveReview(Review review) {
        List<Review> reviews = getAllReviews();
        boolean exists = false;

        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).getId().equals(review.getId())) {
                reviews.set(i, review);
                exists = true;
                break;
            }
        }

        if (!exists) {
            reviews.add(review);
        }

        saveReviewsToFile(reviews);
    }

    public Review findReviewById(String id) {
        List<Review> reviews = getAllReviews();

        for (Review review : reviews) {
            if (review.getId().equals(id)) {
                return review;
            }
        }

        return null;
    }

    public List<Review> getReviewsByAgentId(String agentId) {
        List<Review> reviews = getAllReviews();
        List<Review> agentReviews = new ArrayList<>();

        for (Review review : reviews) {
            if (review.getAgentId().equals(agentId)) {
                agentReviews.add(review);
            }
        }

        return agentReviews;
    }

    public List<Review> getAllReviews() {
        return loadReviewsFromFile();
    }

    private List<Review> loadReviewsFromFile() {
        List<Review> reviews = new ArrayList<>();

        try {
            File file = new File(REVIEWS_FILE);

            if (!file.exists()) {
                return reviews;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Review review = parseReviewFromLine(line);
                    reviews.add(review);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public void saveReviewsToFile(List<Review> reviews) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REVIEWS_FILE))) {
            for (Review review : reviews) {
                writer.write(formatReviewToLine(review));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private Review parseReviewFromLine(String line) {
        String[] parts = line.split("\\|");
        Review review = new Review();

        review.setId(parts[0]);
        review.setUserId(parts[1]);
        review.setAgentId(parts[2]);
        review.setComment(parts[3]);
        review.setRating(Integer.parseInt(parts[4]));
        review.setDateCreated(LocalDateTime.parse(parts[5]));

        return review;
    }

    private String formatReviewToLine(Review review) {
        return String.format("%s|%s|%s|%s|%d|%s",
                review.getId(),
                review.getUserId(),
                review.getAgentId(),
                review.getComment(),
                review.getRating(),
                review.getDateCreated().toString());
    }
}
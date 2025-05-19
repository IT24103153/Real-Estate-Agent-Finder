package com.realestate.agentsystem.controller;

import com.realestate.agentsystem.model.Agent;
import com.realestate.agentsystem.model.Review;
import com.realestate.agentsystem.service.AgentService;
import com.realestate.agentsystem.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final AgentService agentService;

    @Autowired
    public ReviewController(ReviewService reviewService, AgentService agentService) {
        this.reviewService = reviewService;
        this.agentService = agentService;
    }

    @GetMapping
    public String getAllReviews(Model model) {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "review/list";
    }

    @GetMapping("/{id}")
    public String viewReview(@PathVariable String id, Model model) {
        Review review = reviewService.getReviewById(id);
        if (review != null) {
            model.addAttribute("review", review);
            model.addAttribute("agent", agentService.getAgentById(review.getAgentId()));
            return "review/view";
        }
        return "redirect:/reviews";
    }

    @PostMapping("/agent/{agentId}")
    public String addReview(@PathVariable String agentId, @ModelAttribute Review review) {
        review.setId(UUID.randomUUID().toString());
        review.setAgentId(agentId);
        review.setDateCreated(LocalDateTime.now());

        // For demo purposes, use a fixed user ID. In a real app, this would come from authentication
        if (review.getUserId() == null || review.getUserId().isEmpty()) {
            review.setUserId("user123");
        }

        reviewService.saveReview(review);

        // Update the agent's average rating
        agentService.recalculateAgentRating(agentId);

        return "redirect:/agents/" + agentId;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Review review = reviewService.getReviewById(id);
        if (review != null) {
            model.addAttribute("review", review);
            model.addAttribute("agent", agentService.getAgentById(review.getAgentId()));
            return "review/form";
        }
        return "redirect:/reviews";
    }

    @PostMapping("/update")
    public String updateReview(@ModelAttribute Review review) {
        Review existingReview = reviewService.getReviewById(review.getId());
        if (existingReview != null) {
            existingReview.setRating(review.getRating());
            existingReview.setComment(review.getComment());
            reviewService.saveReview(existingReview);

            // Recalculate the agent's average rating
            agentService.recalculateAgentRating(existingReview.getAgentId());

            return "redirect:/agents/" + existingReview.getAgentId();
        }
        return "redirect:/reviews";
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable String id) {
        Review review = reviewService.getReviewById(id);
        if (review != null) {
            String agentId = review.getAgentId();
            reviewService.deleteReview(id);

            // Recalculate the agent's average rating
            agentService.recalculateAgentRating(agentId);

            return "redirect:/agents/" + agentId;
        }
        return "redirect:/reviews";
    }
}
package com.realestate.agentsystem.service;

import com.realestate.agentsystem.model.Agent;
import com.realestate.agentsystem.model.Review;
import com.realestate.agentsystem.repository.AgentFileRepository;
import com.realestate.agentsystem.repository.ReviewFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AgentService {
    private final AgentFileRepository agentRepository;
    private final ReviewFileRepository reviewRepository;

    @Autowired
    public AgentService(AgentFileRepository agentRepository, ReviewFileRepository reviewRepository) {
        this.agentRepository = agentRepository;
        this.reviewRepository = reviewRepository;
    }

    public Agent saveAgent(Agent agent) {
        if (agent.getId() == null || agent.getId().isEmpty()) {
            agent.setId(UUID.randomUUID().toString());
        }
        agentRepository.saveAgent(agent);
        return agent;
    }

    public Agent getAgentById(String id) {
        return agentRepository.findAgentById(id);
    }

    public List<Agent> getAllAgents() {
        return agentRepository.getAllAgents();
    }

    public List<Agent> getAgentsSortedByRating() {
        return agentRepository.getAllAgentsSortedByRating();
    }

    public void addReviewToAgent(String agentId, Review review) {
        // Save the review first
        if (review.getId() == null || review.getId().isEmpty()) {
            review.setId(UUID.randomUUID().toString());
        }
        reviewRepository.saveReview(review);

        // Update the agent with the review
        Agent agent = getAgentById(agentId);
        if (agent != null) {
            // Get all reviews for this agent
            List<Review> agentReviews = reviewRepository.getReviewsByAgentId(agentId);
            agent.setReviews(agentReviews);

            // Recalculate average rating
            double sum = 0;
            for (Review r : agentReviews) {
                sum += r.getRating();
            }
            agent.setAverageRating(sum / agentReviews.size());

            saveAgent(agent);
        }
    }
    public void recalculateAgentRating(String agentId) {
        Agent agent = getAgentById(agentId);
        if (agent != null) {
            List<Review> reviews = reviewRepository.getReviewsByAgentId(agentId);
            agent.setReviews(reviews);

            if (reviews.isEmpty()) {
                agent.setAverageRating(0.0);
            } else {
                double sum = 0;
                for (Review review : reviews) {
                    sum += review.getRating();
                }
                agent.setAverageRating(sum / reviews.size());
            }

            saveAgent(agent);
        }
    }

}
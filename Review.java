package com.realestate.agentsystem.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Review {
    private String id;
    private String userId;
    private String agentId;
    private String comment;
    private int rating;
    private LocalDateTime dateCreated;
}
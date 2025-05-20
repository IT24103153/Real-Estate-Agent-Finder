package com.realestate.agentsystem.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;
import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = true)
public class Agent extends User implements Comparable<Agent> {
    private String specialization;
    private String experience;
    private String location;
    private double averageRating;
    private List<Review> reviews = new ArrayList<>();

    @Override
    public int compareTo(Agent other) {
        return Double.compare(this.averageRating, other.averageRating);
    }
}

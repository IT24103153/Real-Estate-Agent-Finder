package com.realestate.agentsystem.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String role; // ROLE_USER, ROLE_AGENT, ROLE_ADMIN
    private boolean active;
    private LocalDateTime registrationDate;
    private String address;

    // Default constructor
    public User() {
        this.active = true;
        this.registrationDate = LocalDateTime.now();
        this.role = "ROLE_USER"; // Default role
    }
}
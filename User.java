package com.realestate.agentsystem.model;


import lombok.Data;

@Data
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    // Constructors, getters and setters will be generated by Lombok
}
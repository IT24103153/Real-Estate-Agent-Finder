package com.example.entity;

public class Agents implements Comparable<Agents> {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String location;
    private String experience;
    private String specialties;
    private double rating;

    // Constructor
    public Agents() {}

    public Agents(Long id, String name, String email, String phone, String location, String experience, String specialties, double rating) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.experience = experience;
        this.specialties = specialties;
        this.rating = rating;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
    public String getSpecialties() { return specialties; }
    public void setSpecialties(String specialties) { this.specialties = specialties; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    @Override
    public int compareTo(Agents other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return id + "," + name + "," + email + "," + phone + "," + location + "," + experience + "," + specialties + "," + rating;
    }

    public static Agents fromString(String line) {
        String[] parts = line.split(",");
        return new Agents(
                Long.parseLong(parts[0]),
                parts[1],
                parts[2],
                parts[3],
                parts[4],
                parts[5],
                parts[6],
                Double.parseDouble(parts[7])
        );
    }
}
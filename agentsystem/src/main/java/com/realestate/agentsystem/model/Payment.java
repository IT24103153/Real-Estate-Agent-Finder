package com.realestate.agentsystem.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Payment {
    private String id;
    private String userId;
    private String agentId;
    private String appointmentId; // Optional, can be null for direct payments
    private double amount;
    private String currency;
    private String paymentMethod; // CREDIT_CARD, PAYPAL, BANK_TRANSFER, etc.
    private String status; // PENDING, COMPLETED, FAILED, REFUNDED
    private LocalDateTime paymentDate;
    private String transactionReference;
    private String description;
    
    // Default constructor
    public Payment() {
        this.paymentDate = LocalDateTime.now();
        this.status = "PENDING";
        this.currency = "USD"; // Default currency
    }
}

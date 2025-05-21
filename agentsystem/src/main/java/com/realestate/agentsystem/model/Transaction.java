package com.realestate.agentsystem.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private String id;
    private String paymentId;
    private String userId;
    private String agentId;
    private double amount;
    private String type; // PAYMENT, REFUND, FEE, COMMISSION
    private String status; // PENDING, COMPLETED, FAILED
    private LocalDateTime transactionDate;
    private String description;
    
    // Default constructor
    public Transaction() {
        this.transactionDate = LocalDateTime.now();
        this.status = "PENDING";
    }
}

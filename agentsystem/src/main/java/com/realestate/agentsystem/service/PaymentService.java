package com.realestate.agentsystem.service;

import com.realestate.agentsystem.model.Payment;
import com.realestate.agentsystem.model.Transaction;
import com.realestate.agentsystem.repository.PaymentFileRepository;
import com.realestate.agentsystem.repository.TransactionFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentFileRepository paymentRepository;
    private final TransactionFileRepository transactionRepository;
    private TransactionService transactionService;

    @Autowired
    public PaymentService(PaymentFileRepository paymentRepository, TransactionFileRepository transactionRepository) {
        this.paymentRepository = paymentRepository;
        this.transactionRepository = transactionRepository;
    }

    @Autowired
    public void setTransactionService(@Lazy TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Payment createPayment(Payment payment) {
        // Generate ID if null
        if (payment.getId() == null || payment.getId().isEmpty()) {
            payment.setId(UUID.randomUUID().toString());
        }

        // Set default values if not set
        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDateTime.now());
        }

        if (payment.getStatus() == null || payment.getStatus().isEmpty()) {
            payment.setStatus("PENDING");
        }

        if (payment.getCurrency() == null || payment.getCurrency().isEmpty()) {
            payment.setCurrency("USD");
        }

        // Generate transaction reference if not set
        if (payment.getTransactionReference() == null || payment.getTransactionReference().isEmpty()) {
            payment.setTransactionReference("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        // Save payment
        paymentRepository.savePayment(payment);

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setPaymentId(payment.getId());
        transaction.setUserId(payment.getUserId());
        transaction.setAgentId(payment.getAgentId());
        transaction.setAmount(payment.getAmount());
        transaction.setType("PAYMENT");
        transaction.setStatus(payment.getStatus());
        transaction.setDescription("Payment: " + payment.getDescription());

        transactionService.createTransaction(transaction);

        return payment;
    }

    public Payment getPaymentById(String id) {
        return paymentRepository.findPaymentById(id);
    }

    public List<Payment> getPaymentsByUserId(String userId) {
        return paymentRepository.getPaymentsByUserId(userId);
    }

    public List<Payment> getPaymentsByAgentId(String agentId) {
        return paymentRepository.getPaymentsByAgentId(agentId);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }

    public Payment updatePaymentStatus(String id, String status) {
        Payment payment = paymentRepository.findPaymentById(id);
        if (payment != null) {
            String oldStatus = payment.getStatus();
            payment.setStatus(status);
            paymentRepository.savePayment(payment);

            // Create transaction record for status change if needed
            if (!oldStatus.equals(status) && (status.equals("COMPLETED") || status.equals("REFUNDED"))) {
                Transaction transaction = new Transaction();
                transaction.setPaymentId(payment.getId());
                transaction.setUserId(payment.getUserId());
                transaction.setAgentId(payment.getAgentId());
                transaction.setAmount(payment.getAmount());
                transaction.setType(status.equals("REFUNDED") ? "REFUND" : "PAYMENT_CONFIRMATION");
                transaction.setStatus("COMPLETED");
                transaction.setDescription("Payment status changed from " + oldStatus + " to " + status);

                transactionService.createTransaction(transaction);
            }
        }
        return payment;
    }

    public void deletePayment(String id) {
        Payment payment = paymentRepository.findPaymentById(id);
        if (payment != null) {
            payment.setStatus("CANCELLED");
            paymentRepository.savePayment(payment);

            // Create transaction record for cancellation
            Transaction transaction = new Transaction();
            transaction.setPaymentId(payment.getId());
            transaction.setUserId(payment.getUserId());
            transaction.setAgentId(payment.getAgentId());
            transaction.setAmount(payment.getAmount());
            transaction.setType("PAYMENT_CANCELLATION");
            transaction.setStatus("COMPLETED");
            transaction.setDescription("Payment cancelled");

            transactionService.createTransaction(transaction);
        }
    }
}

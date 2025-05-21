package com.realestate.agentsystem.service;

import com.realestate.agentsystem.model.Transaction;
import com.realestate.agentsystem.repository.TransactionFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionFileRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionFileRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transaction) {
        // Generate ID if null
        if (transaction.getId() == null || transaction.getId().isEmpty()) {
            transaction.setId(UUID.randomUUID().toString());
        }

        // Set default values if not set
        if (transaction.getTransactionDate() == null) {
            transaction.setTransactionDate(LocalDateTime.now());
        }
        
        if (transaction.getStatus() == null || transaction.getStatus().isEmpty()) {
            transaction.setStatus("PENDING");
        }
        
        // Save transaction
        transactionRepository.saveTransaction(transaction);
        return transaction;
    }

    public Transaction getTransactionById(String id) {
        return transactionRepository.findTransactionById(id);
    }

    public List<Transaction> getTransactionsByUserId(String userId) {
        return transactionRepository.getTransactionsByUserId(userId);
    }

    public List<Transaction> getTransactionsByAgentId(String agentId) {
        return transactionRepository.getTransactionsByAgentId(agentId);
    }

    public List<Transaction> getTransactionsByPaymentId(String paymentId) {
        return transactionRepository.getTransactionsByPaymentId(paymentId);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }

    public Transaction updateTransactionStatus(String id, String status) {
        Transaction transaction = transactionRepository.findTransactionById(id);
        if (transaction != null) {
            transaction.setStatus(status);
            transactionRepository.saveTransaction(transaction);
        }
        return transaction;
    }
}

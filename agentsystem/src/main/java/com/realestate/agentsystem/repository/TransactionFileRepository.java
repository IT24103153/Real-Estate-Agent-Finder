package com.realestate.agentsystem.repository;

import com.realestate.agentsystem.model.Transaction;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionFileRepository {
    private static final String TRANSACTIONS_FILE = "transactions.txt";

    public TransactionFileRepository() {
        // Initialize file if it doesn't exist
        try {
            File file = new File(TRANSACTIONS_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTransaction(Transaction transaction) {
        // Generate ID if null
        if (transaction.getId() == null || transaction.getId().isEmpty()) {
            transaction.setId(UUID.randomUUID().toString());
        }

        List<Transaction> transactions = getAllTransactions();
        boolean exists = false;

        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId().equals(transaction.getId())) {
                transactions.set(i, transaction);
                exists = true;
                break;
            }
        }

        if (!exists) {
            transactions.add(transaction);
        }

        saveTransactionsToFile(transactions);
    }

    public Transaction findTransactionById(String id) {
        List<Transaction> transactions = getAllTransactions();

        for (Transaction transaction : transactions) {
            if (transaction.getId().equals(id)) {
                return transaction;
            }
        }

        return null;
    }

    public List<Transaction> getTransactionsByUserId(String userId) {
        List<Transaction> transactions = getAllTransactions();
        List<Transaction> userTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getUserId().equals(userId)) {
                userTransactions.add(transaction);
            }
        }

        return userTransactions;
    }

    public List<Transaction> getTransactionsByAgentId(String agentId) {
        List<Transaction> transactions = getAllTransactions();
        List<Transaction> agentTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getAgentId() != null && transaction.getAgentId().equals(agentId)) {
                agentTransactions.add(transaction);
            }
        }

        return agentTransactions;
    }

    public List<Transaction> getTransactionsByPaymentId(String paymentId) {
        List<Transaction> transactions = getAllTransactions();
        List<Transaction> paymentTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getPaymentId().equals(paymentId)) {
                paymentTransactions.add(transaction);
            }
        }

        return paymentTransactions;
    }

    public List<Transaction> getAllTransactions() {
        return loadTransactionsFromFile();
    }

    private List<Transaction> loadTransactionsFromFile() {
        List<Transaction> transactions = new ArrayList<>();

        try {
            File file = new File(TRANSACTIONS_FILE);

            if (!file.exists() || file.length() == 0) {
                return transactions;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Transaction transaction = parseTransactionFromLine(line);
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    private void saveTransactionsToFile(List<Transaction> transactions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE))) {
            for (Transaction transaction : transactions) {
                writer.write(formatTransactionToLine(transaction));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Transaction parseTransactionFromLine(String line) {
        String[] parts = line.split("\\|");
        Transaction transaction = new Transaction();

        // Handle case where the file might be empty or have incomplete data
        if (parts.length > 0)
            transaction.setId(parts[0]);
        if (parts.length > 1)
            transaction.setPaymentId(parts[1]);
        if (parts.length > 2)
            transaction.setUserId(parts[2]);
        if (parts.length > 3 && !parts[3].isEmpty())
            transaction.setAgentId(parts[3]);

        if (parts.length > 4) {
            try {
                transaction.setAmount(Double.parseDouble(parts[4]));
            } catch (Exception e) {
                transaction.setAmount(0.0);
            }
        }

        if (parts.length > 5)
            transaction.setType(parts[5]);
        if (parts.length > 6)
            transaction.setStatus(parts[6]);

        if (parts.length > 7) {
            try {
                transaction.setTransactionDate(LocalDateTime.parse(parts[7]));
            } catch (Exception e) {
                transaction.setTransactionDate(LocalDateTime.now());
            }
        }

        if (parts.length > 8)
            transaction.setDescription(parts[8]);

        return transaction;
    }

    private String formatTransactionToLine(Transaction transaction) {
        return String.format("%s|%s|%s|%s|%f|%s|%s|%s|%s",
                transaction.getId(),
                transaction.getPaymentId() != null ? transaction.getPaymentId() : "",
                transaction.getUserId() != null ? transaction.getUserId() : "",
                transaction.getAgentId() != null ? transaction.getAgentId() : "",
                transaction.getAmount(),
                transaction.getType() != null ? transaction.getType() : "",
                transaction.getStatus() != null ? transaction.getStatus() : "PENDING",
                transaction.getTransactionDate() != null ? transaction.getTransactionDate() : LocalDateTime.now(),
                transaction.getDescription() != null ? transaction.getDescription() : "");
    }
}

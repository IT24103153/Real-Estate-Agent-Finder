package com.realestate.agentsystem.repository;

import com.realestate.agentsystem.model.Payment;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PaymentFileRepository {
    private static final String PAYMENTS_FILE = "payments.txt";

    public PaymentFileRepository() {
        // Initialize file if it doesn't exist
        try {
            File file = new File(PAYMENTS_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePayment(Payment payment) {
        // Generate ID if null
        if (payment.getId() == null || payment.getId().isEmpty()) {
            payment.setId(UUID.randomUUID().toString());
        }

        List<Payment> payments = getAllPayments();
        boolean exists = false;

        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getId().equals(payment.getId())) {
                payments.set(i, payment);
                exists = true;
                break;
            }
        }

        if (!exists) {
            payments.add(payment);
        }

        savePaymentsToFile(payments);
    }

    public Payment findPaymentById(String id) {
        List<Payment> payments = getAllPayments();

        for (Payment payment : payments) {
            if (payment.getId().equals(id)) {
                return payment;
            }
        }

        return null;
    }

    public List<Payment> getPaymentsByUserId(String userId) {
        List<Payment> payments = getAllPayments();
        List<Payment> userPayments = new ArrayList<>();

        for (Payment payment : payments) {
            if (payment.getUserId().equals(userId)) {
                userPayments.add(payment);
            }
        }

        return userPayments;
    }

    public List<Payment> getPaymentsByAgentId(String agentId) {
        List<Payment> payments = getAllPayments();
        List<Payment> agentPayments = new ArrayList<>();

        for (Payment payment : payments) {
            if (payment.getAgentId() != null && payment.getAgentId().equals(agentId)) {
                agentPayments.add(payment);
            }
        }

        return agentPayments;
    }

    public List<Payment> getAllPayments() {
        return loadPaymentsFromFile();
    }

    private List<Payment> loadPaymentsFromFile() {
        List<Payment> payments = new ArrayList<>();

        try {
            File file = new File(PAYMENTS_FILE);

            if (!file.exists() || file.length() == 0) {
                return payments;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Payment payment = parsePaymentFromLine(line);
                    payments.add(payment);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return payments;
    }

    private void savePaymentsToFile(List<Payment> payments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PAYMENTS_FILE))) {
            for (Payment payment : payments) {
                writer.write(formatPaymentToLine(payment));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Payment parsePaymentFromLine(String line) {
        String[] parts = line.split("\\|");
        Payment payment = new Payment();

        // Handle case where the file might be empty or have incomplete data
        if (parts.length > 0)
            payment.setId(parts[0]);
        if (parts.length > 1)
            payment.setUserId(parts[1]);
        if (parts.length > 2 && !parts[2].isEmpty())
            payment.setAgentId(parts[2]);
        if (parts.length > 3 && !parts[3].isEmpty())
            payment.setAppointmentId(parts[3]);

        if (parts.length > 4) {
            try {
                payment.setAmount(Double.parseDouble(parts[4]));
            } catch (Exception e) {
                payment.setAmount(0.0);
            }
        }

        if (parts.length > 5)
            payment.setCurrency(parts[5]);
        if (parts.length > 6)
            payment.setPaymentMethod(parts[6]);
        if (parts.length > 7)
            payment.setStatus(parts[7]);

        if (parts.length > 8) {
            try {
                payment.setPaymentDate(LocalDateTime.parse(parts[8]));
            } catch (Exception e) {
                payment.setPaymentDate(LocalDateTime.now());
            }
        }

        if (parts.length > 9)
            payment.setTransactionReference(parts[9]);
        if (parts.length > 10)
            payment.setDescription(parts[10]);

        return payment;
    }

    private String formatPaymentToLine(Payment payment) {
        return String.format("%s|%s|%s|%s|%f|%s|%s|%s|%s|%s|%s",
                payment.getId(),
                payment.getUserId() != null ? payment.getUserId() : "",
                payment.getAgentId() != null ? payment.getAgentId() : "",
                payment.getAppointmentId() != null ? payment.getAppointmentId() : "",
                payment.getAmount(),
                payment.getCurrency() != null ? payment.getCurrency() : "USD",
                payment.getPaymentMethod() != null ? payment.getPaymentMethod() : "",
                payment.getStatus() != null ? payment.getStatus() : "PENDING",
                payment.getPaymentDate() != null ? payment.getPaymentDate() : LocalDateTime.now(),
                payment.getTransactionReference() != null ? payment.getTransactionReference() : "",
                payment.getDescription() != null ? payment.getDescription() : "");
    }
}

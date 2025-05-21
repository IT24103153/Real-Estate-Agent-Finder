package com.realestate.agentsystem.controller;

import com.realestate.agentsystem.model.Transaction;
import com.realestate.agentsystem.model.User;
import com.realestate.agentsystem.service.TransactionService;
import com.realestate.agentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping
    public String listTransactions(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        
        if (user.getRole().equals("ROLE_ADMIN")) {
            model.addAttribute("transactions", transactionService.getAllTransactions());
        } else if (user.getRole().equals("ROLE_AGENT")) {
            model.addAttribute("transactions", transactionService.getTransactionsByAgentId(user.getId()));
        } else {
            model.addAttribute("transactions", transactionService.getTransactionsByUserId(user.getId()));
        }
        
        return "transaction/list";
    }

    @GetMapping("/{id}")
    public String viewTransaction(@PathVariable String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        Transaction transaction = transactionService.getTransactionById(id);
        
        // Check if user has permission to view this transaction
        if (transaction != null && (user.getRole().equals("ROLE_ADMIN") || 
                                   transaction.getUserId().equals(user.getId()) || 
                                   (user.getRole().equals("ROLE_AGENT") && transaction.getAgentId() != null && transaction.getAgentId().equals(user.getId())))) {
            model.addAttribute("transaction", transaction);
            return "transaction/view";
        }
        
        return "redirect:/transactions?error=unauthorized";
    }

    @GetMapping("/payment/{paymentId}")
    public String listTransactionsByPayment(@PathVariable String paymentId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        
        // Get transactions for this payment
        model.addAttribute("transactions", transactionService.getTransactionsByPaymentId(paymentId));
        model.addAttribute("paymentId", paymentId);
        
        return "transaction/list-by-payment";
    }

    @GetMapping("/{id}/status")
    public String updateTransactionStatusForm(@PathVariable String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        Transaction transaction = transactionService.getTransactionById(id);
        
        // Check if user has permission to update this transaction (admin only)
        if (transaction != null && user.getRole().equals("ROLE_ADMIN")) {
            model.addAttribute("transaction", transaction);
            return "transaction/status-form";
        }
        
        return "redirect:/transactions?error=unauthorized";
    }

    @PostMapping("/{id}/status")
    public String updateTransactionStatus(@PathVariable String id, @RequestParam String status) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        Transaction transaction = transactionService.getTransactionById(id);
        
        // Check if user has permission to update this transaction (admin only)
        if (transaction != null && user.getRole().equals("ROLE_ADMIN")) {
            transactionService.updateTransactionStatus(id, status);
            return "redirect:/transactions/" + id + "?updated";
        }
        
        return "redirect:/transactions?error=unauthorized";
    }
}

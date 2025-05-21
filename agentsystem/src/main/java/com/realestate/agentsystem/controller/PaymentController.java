package com.realestate.agentsystem.controller;

import com.realestate.agentsystem.model.Payment;
import com.realestate.agentsystem.model.User;
import com.realestate.agentsystem.service.AgentService;
import com.realestate.agentsystem.service.PaymentService;
import com.realestate.agentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final AgentService agentService;

    @Autowired
    public PaymentController(PaymentService paymentService, UserService userService, AgentService agentService) {
        this.paymentService = paymentService;
        this.userService = userService;
        this.agentService = agentService;
    }

    @GetMapping
    public String listPayments(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        
        if (user.getRole().equals("ROLE_ADMIN")) {
            model.addAttribute("payments", paymentService.getAllPayments());
        } else if (user.getRole().equals("ROLE_AGENT")) {
            model.addAttribute("payments", paymentService.getPaymentsByAgentId(user.getId()));
        } else {
            model.addAttribute("payments", paymentService.getPaymentsByUserId(user.getId()));
        }
        
        return "payment/list";
    }

    @GetMapping("/{id}")
    public String viewPayment(@PathVariable String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        Payment payment = paymentService.getPaymentById(id);
        
        // Check if user has permission to view this payment
        if (payment != null && (user.getRole().equals("ROLE_ADMIN") || 
                               payment.getUserId().equals(user.getId()) || 
                               (user.getRole().equals("ROLE_AGENT") && payment.getAgentId() != null && payment.getAgentId().equals(user.getId())))) {
            model.addAttribute("payment", payment);
            return "payment/view";
        }
        
        return "redirect:/payments?error=unauthorized";
    }

    @GetMapping("/new")
    public String newPaymentForm(Model model, @RequestParam(required = false) String agentId) {
        Payment payment = new Payment();
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        payment.setUserId(user.getId());
        
        if (agentId != null && !agentId.isEmpty()) {
            payment.setAgentId(agentId);
            model.addAttribute("agent", agentService.getAgentById(agentId));
        }
        
        model.addAttribute("payment", payment);
        return "payment/form";
    }

    @PostMapping
    public String createPayment(@ModelAttribute Payment payment) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        
        // Ensure the user can only create payments for themselves
        if (!user.getId().equals(payment.getUserId()) && !user.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/payments?error=unauthorized";
        }
        
        paymentService.createPayment(payment);
        return "redirect:/payments";
    }

    @GetMapping("/{id}/status")
    public String updatePaymentStatusForm(@PathVariable String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        Payment payment = paymentService.getPaymentById(id);
        
        // Check if user has permission to update this payment
        if (payment != null && (user.getRole().equals("ROLE_ADMIN") || 
                               (user.getRole().equals("ROLE_AGENT") && payment.getAgentId() != null && payment.getAgentId().equals(user.getId())))) {
            model.addAttribute("payment", payment);
            return "payment/status-form";
        }
        
        return "redirect:/payments?error=unauthorized";
    }

    @PostMapping("/{id}/status")
    public String updatePaymentStatus(@PathVariable String id, @RequestParam String status) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        Payment payment = paymentService.getPaymentById(id);
        
        // Check if user has permission to update this payment
        if (payment != null && (user.getRole().equals("ROLE_ADMIN") || 
                               (user.getRole().equals("ROLE_AGENT") && payment.getAgentId() != null && payment.getAgentId().equals(user.getId())))) {
            paymentService.updatePaymentStatus(id, status);
            return "redirect:/payments/" + id + "?updated";
        }
        
        return "redirect:/payments?error=unauthorized";
    }

    @GetMapping("/{id}/delete")
    public String deletePayment(@PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        Payment payment = paymentService.getPaymentById(id);
        
        // Check if user has permission to delete this payment
        if (payment != null && (user.getRole().equals("ROLE_ADMIN") || 
                               payment.getUserId().equals(user.getId()))) {
            paymentService.deletePayment(id);
            return "redirect:/payments?deleted";
        }
        
        return "redirect:/payments?error=unauthorized";
    }
}

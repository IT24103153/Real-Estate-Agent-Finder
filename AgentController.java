package com.realestate.agentsystem.controller;

import com.realestate.agentsystem.model.Agent;
import com.realestate.agentsystem.model.Appointment;
import com.realestate.agentsystem.model.Review;
import com.realestate.agentsystem.service.AgentService;
import com.realestate.agentsystem.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/agents")
public class AgentController {
    private final AgentService agentService;
    private final AppointmentService appointmentService;

    @Autowired
    public AgentController(AgentService agentService, AppointmentService appointmentService) {
        this.agentService = agentService;
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public String listAgents(Model model) {
        model.addAttribute("agents", agentService.getAllAgents());
        return "agent/list";
    }

    @GetMapping("/sorted")
    public String listAgentsSorted(Model model) {
        model.addAttribute("agents", agentService.getAgentsSortedByRating());
        return "agent/list";
    }

    @GetMapping("/{id}")
    public String viewAgent(@PathVariable String id, Model model) {
        model.addAttribute("agent", agentService.getAgentById(id));
        return "agent/view";
    }

    @GetMapping("/new")
    public String newAgentForm(Model model) {
        model.addAttribute("agent", new Agent());
        return "agent/form";
    }

    @PostMapping
    public String saveAgent(@ModelAttribute Agent agent) {
        agentService.saveAgent(agent);
        return "redirect:/agents";
    }

    @GetMapping("/{id}/edit")
    public String editAgentForm(@PathVariable String id, Model model) {
        model.addAttribute("agent", agentService.getAgentById(id));
        return "agent/form";
    }

    @GetMapping("/{id}/book")
    public String bookAppointmentForm(@PathVariable String id, Model model) {
        model.addAttribute("agent", agentService.getAgentById(id));
        model.addAttribute("appointment", new Appointment());
        return "appointment/form";
    }

    @PostMapping("/{id}/review")
    public String addReview(@PathVariable String id, @ModelAttribute Review review) {
        review.setId(UUID.randomUUID().toString());
        review.setAgentId(id);
        review.setDateCreated(LocalDateTime.now());
        agentService.addReviewToAgent(id, review);
        return "redirect:/agents/" + id;
    }
}
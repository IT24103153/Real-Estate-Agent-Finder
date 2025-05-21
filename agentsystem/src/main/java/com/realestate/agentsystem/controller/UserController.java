package com.realestate.agentsystem.controller;

import com.realestate.agentsystem.model.User;
import com.realestate.agentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        try {
            userService.registerUser(user);
            return "redirect:/login?registered";
        } catch (RuntimeException e) {
            return "redirect:/register?error=" + e.getMessage();
        }
    }

    @GetMapping("/user/profile")
    public String userProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/user/edit")
    public String editUserForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        model.addAttribute("user", user);
        return "user/edit";
    }

    @PostMapping("/user/update")
    public String updateUser(@ModelAttribute User user) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userService.getUserByEmail(auth.getName());
            
            // Ensure the user can only update their own profile
            if (!currentUser.getId().equals(user.getId())) {
                return "redirect:/user/profile?error=unauthorized";
            }
            
            userService.updateUser(user);
            return "redirect:/user/profile?updated";
        } catch (RuntimeException e) {
            return "redirect:/user/edit?error=" + e.getMessage();
        }
    }

    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/admin/users/{id}")
    public String viewUser(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/user-view";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String adminEditUserForm(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/user-edit";
    }

    @PostMapping("/admin/users/update")
    public String adminUpdateUser(@ModelAttribute User user) {
        try {
            userService.updateUser(user);
            return "redirect:/admin/users?updated";
        } catch (RuntimeException e) {
            return "redirect:/admin/users/" + user.getId() + "/edit?error=" + e.getMessage();
        }
    }

    @GetMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "redirect:/admin/users?deleted";
    }
}

package com.realestate.agentsystem.service;

import com.realestate.agentsystem.model.User;
import com.realestate.agentsystem.repository.UserFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserFileRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserFileRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        // Save user directly without validation
        userRepository.saveUser(user);
        return user;
    }

    public User registerUser(User user) {
        // Check if email already exists
        if (userRepository.findUserByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }

        // Set default values
        user.setId(UUID.randomUUID().toString());
        user.setActive(true);
        user.setRegistrationDate(LocalDateTime.now());

        // If role is not set, set default role
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_USER");
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user
        userRepository.saveUser(user);
        return user;
    }

    public User getUserById(String id) {
        return userRepository.findUserById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User updateUser(User user) {
        // Get existing user
        User existingUser = userRepository.findUserById(user.getId());
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

        // Check if email is being changed and if it already exists
        if (!existingUser.getEmail().equals(user.getEmail()) &&
                userRepository.findUserByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }

        // If password is empty, use existing password
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(existingUser.getPassword());
        } else {
            // Encode new password
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Preserve registration date
        user.setRegistrationDate(existingUser.getRegistrationDate());

        // Save updated user
        userRepository.saveUser(user);
        return user;
    }

    public void deleteUser(String id) {
        User user = userRepository.findUserById(id);
        if (user != null) {
            user.setActive(false);
            userRepository.saveUser(user);
        }
    }

    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findUserByEmail(email);
        if (user != null && user.isActive()) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}

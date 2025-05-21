package com.realestate.agentsystem.repository;

import com.realestate.agentsystem.model.User;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserFileRepository {
    private static final String USERS_FILE = "users.txt";

    public UserFileRepository() {
        // Initialize file if it doesn't exist
        try {
            File file = new File(USERS_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(User user) {
        // Generate ID if null
        if (user.getId() == null || user.getId().isEmpty()) {
            user.setId(UUID.randomUUID().toString());
        }

        List<User> users = getAllUsers();
        boolean exists = false;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                exists = true;
                break;
            }
        }

        if (!exists) {
            users.add(user);
        }

        saveUsersToFile(users);
    }

    public User findUserById(String id) {
        List<User> users = getAllUsers();

        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }

        return null;
    }

    public User findUserByEmail(String email) {
        List<User> users = getAllUsers();

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }

        return null;
    }

    public List<User> getAllUsers() {
        return loadUsersFromFile();
    }

    private List<User> loadUsersFromFile() {
        List<User> users = new ArrayList<>();

        try {
            File file = new File(USERS_FILE);

            if (!file.exists() || file.length() == 0) {
                return users;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    User user = parseUserFromLine(line);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    private void saveUsersToFile(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.write(formatUserToLine(user));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User parseUserFromLine(String line) {
        String[] parts = line.split("\\|");
        User user = new User();

        // Handle case where the file might be empty or have incomplete data
        if (parts.length > 0)
            user.setId(parts[0]);
        if (parts.length > 1)
            user.setName(parts[1]);
        if (parts.length > 2)
            user.setEmail(parts[2]);
        if (parts.length > 3)
            user.setPassword(parts[3]);
        if (parts.length > 4)
            user.setPhoneNumber(parts[4]);
        if (parts.length > 5)
            user.setRole(parts[5]);
        if (parts.length > 6)
            user.setActive(Boolean.parseBoolean(parts[6]));
        if (parts.length > 7) {
            try {
                user.setRegistrationDate(LocalDateTime.parse(parts[7]));
            } catch (Exception e) {
                user.setRegistrationDate(LocalDateTime.now());
            }
        }
        if (parts.length > 8)
            user.setAddress(parts[8]);

        return user;
    }

    private String formatUserToLine(User user) {
        return String.format("%s|%s|%s|%s|%s|%s|%b|%s|%s",
                user.getId(),
                user.getName() != null ? user.getName() : "",
                user.getEmail() != null ? user.getEmail() : "",
                user.getPassword() != null ? user.getPassword() : "",
                user.getPhoneNumber() != null ? user.getPhoneNumber() : "",
                user.getRole() != null ? user.getRole() : "ROLE_USER",
                user.isActive(),
                user.getRegistrationDate() != null ? user.getRegistrationDate() : LocalDateTime.now(),
                user.getAddress() != null ? user.getAddress() : "");
    }
}

package com.example.util;

import com.example.entity.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserService {
    private static final String USER_FILE = "users.txt";
    private static UserService instance;
    
    private UserService() {
        ensureFileExists();
    }
    
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    
    private void ensureFileExists() {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
                createDefaultUsers();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void createDefaultUsers() {
        List<User> defaultUsers = new ArrayList<>();
        defaultUsers.add(new User(UUID.randomUUID().toString(), "buyer1", "password", "John Buyer", "buyer1@example.com", "555-1234", "BUYER"));
        defaultUsers.add(new User(UUID.randomUUID().toString(), "seller1", "password", "Jane Seller", "seller1@example.com", "555-5678", "SELLER"));
        defaultUsers.add(new User(UUID.randomUUID().toString(), "agent1", "password", "Mark Agent", "agent1@example.com", "555-9012", "AGENT"));
        
        saveAllUsers(defaultUsers);
    }
    
    public List<User> getAllUsers() {
        try {
            Path path = Paths.get(USER_FILE);
            if (Files.size(path) == 0) {
                return new ArrayList<>();
            }
            
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_FILE))) {
                return (List<User>) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    private void saveAllUsers(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public User createUser(User user) {
        List<User> users = getAllUsers();
        
        if (getUserByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        
        user.setId(UUID.randomUUID().toString());
        users.add(user);
        saveAllUsers(users);
        return user;
    }
    
    public Optional<User> getUserByUsername(String username) {
        return getAllUsers().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }
    
    public Optional<User> getUserById(String id) {
        return getAllUsers().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }
    
    public User updateUser(User user) {
        List<User> users = getAllUsers();
        
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                saveAllUsers(users);
                return user;
            }
        }
        
        throw new RuntimeException("User not found");
    }
    
    public void deleteUser(String id) {
        List<User> users = getAllUsers();
        List<User> updatedUsers = users.stream()
                .filter(u -> !u.getId().equals(id))
                .collect(Collectors.toList());
        
        if (users.size() == updatedUsers.size()) {
            throw new RuntimeException("User not found");
        }
        
        saveAllUsers(updatedUsers);
    }
    
    public Optional<User> authenticate(String username, String password) {
        return getAllUsers().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst();
    }
} 
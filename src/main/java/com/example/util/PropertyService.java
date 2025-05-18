package com.example.util;

import com.example.entity.Property;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PropertyService {
    private static final String PROPERTY_FILE = "properties.txt";
    private static PropertyService instance;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private PropertyService() {
        ensureFileExists();
    }

    public static PropertyService getInstance() {
        if (instance == null) {
            instance = new PropertyService();
        }
        return instance;
    }

    private void ensureFileExists() {
        File file = new File(PROPERTY_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
                objectMapper.writeValue(file, new ArrayList<Property>());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Property> getAllProperties() {
        try {
            File file = new File(PROPERTY_FILE);
            if (file.length() == 0) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, new TypeReference<List<Property>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Property createProperty(Property property) {
        List<Property> properties = getAllProperties();
        property.setId(UUID.randomUUID().toString());
        properties.add(property);
        saveAllProperties(properties);
        return property;
    }

    public Optional<Property> getPropertyById(String id) {
        return getAllProperties().stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public Property updateProperty(Property property) {
        List<Property> properties = getAllProperties();
        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getId().equals(property.getId())) {
                properties.set(i, property);
                saveAllProperties(properties);
                return property;
            }
        }
        throw new RuntimeException("Property not found");
    }

    public void deleteProperty(String id) {
        List<Property> properties = getAllProperties();
        List<Property> updated = new ArrayList<>();
        for (Property p : properties) {
            if (!p.getId().equals(id)) {
                updated.add(p);
            }
        }
        saveAllProperties(updated);
    }

    private void saveAllProperties(List<Property> properties) {
        try {
            objectMapper.writeValue(new File(PROPERTY_FILE), properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 
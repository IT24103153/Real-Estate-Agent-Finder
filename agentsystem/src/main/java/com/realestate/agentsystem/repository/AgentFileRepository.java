package com.realestate.agentsystem.repository;

import com.realestate.agentsystem.model.Agent;
import com.realestate.agentsystem.util.datastructure.BinarySearchTree;
import com.realestate.agentsystem.util.datastructure.SelectionSort;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AgentFileRepository {
    private static final String AGENTS_FILE = "agents.txt";
    private final BinarySearchTree<Agent> agentBST = new BinarySearchTree<>();

    public AgentFileRepository() {
        // Initialize file if it doesn't exist
        try {
            File file = new File(AGENTS_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAgent(Agent agent) {
        // Generate ID if null
        if (agent.getId() == null || agent.getId().isEmpty()) {
            agent.setId(UUID.randomUUID().toString());
        }

        List<Agent> agents = getAllAgents();
        boolean exists = false;

        for (int i = 0; i < agents.size(); i++) {
            // Check for null IDs on both sides
            String currentId = agents.get(i).getId();
            String newId = agent.getId();

            if (currentId != null && newId != null && currentId.equals(newId)) {
                agents.set(i, agent);
                exists = true;
                break;
            }
        }

        if (!exists) {
            agents.add(agent);
        }

        saveAgentsToFile(agents);

        // Update BST
        agentBST.insert(agent);
    }

    public Agent findAgentById(String id) {
        if (id == null) {
            return null;
        }

        List<Agent> agents = getAllAgents();

        for (Agent agent : agents) {
            if (id.equals(agent.getId())) {
                return agent;
            }
        }

        return null;
    }

    public List<Agent> getAllAgents() {
        return loadAgentsFromFile();
    }

    public List<Agent> getAllAgentsSortedByRating() {
        List<Agent> agents = getAllAgents();
        SelectionSort.sortAgentsByRating(agents);
        return agents;
    }

    private List<Agent> loadAgentsFromFile() {
        List<Agent> agents = new ArrayList<>();

        try {
            File file = new File(AGENTS_FILE);

            if (!file.exists() || file.length() == 0) {
                return agents;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Agent agent = parseAgentFromLine(line);
                    if (agent != null) {
                        agents.add(agent);
                        agentBST.insert(agent);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return agents;
    }

    private void saveAgentsToFile(List<Agent> agents) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AGENTS_FILE))) {
            for (Agent agent : agents) {
                writer.write(formatAgentToLine(agent));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Agent parseAgentFromLine(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length < 7) {
                return null;
            }

            Agent agent = new Agent();
            agent.setId(parts[0]);
            agent.setName(parts[1]);
            agent.setEmail(parts[2]);
            agent.setPhoneNumber(parts[3]);
            agent.setSpecialization(parts[4]);
            agent.setExperience(parts[5]);
            agent.setLocation(parts[6]);

            if (parts.length > 7) {
                agent.setAverageRating(Double.parseDouble(parts[7]));
            }

            return agent;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String formatAgentToLine(Agent agent) {
        return String.format("%s|%s|%s|%s|%s|%s|%s|%f",
                agent.getId(),
                agent.getName() != null ? agent.getName() : "",
                agent.getEmail() != null ? agent.getEmail() : "",
                agent.getPhoneNumber() != null ? agent.getPhoneNumber() : "",
                agent.getSpecialization() != null ? agent.getSpecialization() : "",
                agent.getExperience() != null ? agent.getExperience() : "",
                agent.getLocation() != null ? agent.getLocation() : "",
                agent.getAverageRating());
    }
}
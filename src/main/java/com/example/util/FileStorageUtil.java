package com.example.util;

import com.example.entity.Agents;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorageUtil {
    private static final String FILE_PATH = "agents.txt";

    public static void writeAgents(List<Agents> agents) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Agents agent : agents) {
                writer.write(agent.toString());
                writer.newLine();
            }
        }
    }

    public static List<Agents> readAgents() throws IOException {
        List<Agents> agents = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
            return agents;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    agents.add(Agents.fromString(line));
                }
            }
        }
        return agents;
    }
}
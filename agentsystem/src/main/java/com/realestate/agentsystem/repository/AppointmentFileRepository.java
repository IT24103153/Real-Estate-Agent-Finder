package com.realestate.agentsystem.repository;

import com.realestate.agentsystem.model.Appointment;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AppointmentFileRepository {
    private static final String APPOINTMENTS_FILE = "appointments.txt";

    public AppointmentFileRepository() {
        // Initialize file if it doesn't exist
        try {
            File file = new File(APPOINTMENTS_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAppointment(Appointment appointment) {
        List<Appointment> appointments = getAllAppointments();
        boolean exists = false;

        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getId().equals(appointment.getId())) {
                appointments.set(i, appointment);
                exists = true;
                break;
            }
        }

        if (!exists) {
            appointments.add(appointment);
        }

        saveAppointmentsToFile(appointments);
    }

    public Appointment findAppointmentById(String id) {
        List<Appointment> appointments = getAllAppointments();

        for (Appointment appointment : appointments) {
            if (appointment.getId().equals(id)) {
                return appointment;
            }
        }

        return null;
    }

    public List<Appointment> getAppointmentsByAgentId(String agentId) {
        List<Appointment> appointments = getAllAppointments();
        List<Appointment> agentAppointments = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if (appointment.getAgentId().equals(agentId)) {
                agentAppointments.add(appointment);
            }
        }

        return agentAppointments;
    }

    public List<Appointment> getAppointmentsByUserId(String userId) {
        List<Appointment> appointments = getAllAppointments();
        List<Appointment> userAppointments = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if (appointment.getUserId().equals(userId)) {
                userAppointments.add(appointment);
            }
        }

        return userAppointments;
    }

    public List<Appointment> getAllAppointments() {
        return loadAppointmentsFromFile();
    }

    private List<Appointment> loadAppointmentsFromFile() {
        List<Appointment> appointments = new ArrayList<>();

        try {
            File file = new File(APPOINTMENTS_FILE);

            if (!file.exists()) {
                return appointments;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Appointment appointment = parseAppointmentFromLine(line);
                    appointments.add(appointment);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    private void saveAppointmentsToFile(List<Appointment> appointments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(APPOINTMENTS_FILE))) {
            for (Appointment appointment : appointments) {
                writer.write(formatAppointmentToLine(appointment));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Appointment parseAppointmentFromLine(String line) {
        String[] parts = line.split("\\|");
        Appointment appointment = new Appointment();

        appointment.setId(parts[0]);
        appointment.setUserId(parts[1]);
        appointment.setAgentId(parts[2]);
        appointment.setDateTime(LocalDateTime.parse(parts[3]));
        appointment.setStatus(parts[4]);
        appointment.setPropertyType(parts[5]);
        appointment.setLocation(parts[6]);

        return appointment;
    }

    private String formatAppointmentToLine(Appointment appointment) {
        return String.format("%s|%s|%s|%s|%s|%s|%s",
                appointment.getId(),
                appointment.getUserId(),
                appointment.getAgentId(),
                appointment.getDateTime().toString(),
                appointment.getStatus(),
                appointment.getPropertyType(),
                appointment.getLocation());
    }
}
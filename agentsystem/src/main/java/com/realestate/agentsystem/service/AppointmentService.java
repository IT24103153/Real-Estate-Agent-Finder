package com.realestate.agentsystem.service;

import com.realestate.agentsystem.model.Appointment;
import com.realestate.agentsystem.repository.AppointmentFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {
    private final AppointmentFileRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentFileRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment saveAppointment(Appointment appointment) {
        if (appointment.getId() == null || appointment.getId().isEmpty()) {
            appointment.setId(UUID.randomUUID().toString());
        }

        // Set default status if not set
        if (appointment.getStatus() == null || appointment.getStatus().isEmpty()) {
            appointment.setStatus("SCHEDULED");
        }

        appointmentRepository.saveAppointment(appointment);
        return appointment;
    }

    public Appointment getAppointmentById(String id) {
        return appointmentRepository.findAppointmentById(id);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.getAllAppointments();
    }

    public List<Appointment> getAppointmentsByAgentId(String agentId) {
        return appointmentRepository.getAppointmentsByAgentId(agentId);
    }

    public List<Appointment> getAppointmentsByUserId(String userId) {
        return appointmentRepository.getAppointmentsByUserId(userId);
    }

    public Appointment updateAppointmentStatus(String id, String status) {
        Appointment appointment = getAppointmentById(id);
        if (appointment != null) {
            appointment.setStatus(status);
            appointmentRepository.saveAppointment(appointment);
        }
        return appointment;
    }

    public boolean cancelAppointment(String id) {
        Appointment appointment = getAppointmentById(id);
        if (appointment != null) {
            appointment.setStatus("CANCELLED");
            appointmentRepository.saveAppointment(appointment);
            return true;
        }
        return false;
    }

    public boolean completeAppointment(String id) {
        Appointment appointment = getAppointmentById(id);
        if (appointment != null) {
            appointment.setStatus("COMPLETED");
            appointmentRepository.saveAppointment(appointment);
            return true;
        }
        return false;
    }
}
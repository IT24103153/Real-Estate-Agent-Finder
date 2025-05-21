package com.realestate.agentsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.realestate.agentsystem.service.AgentService;
import com.realestate.agentsystem.service.UserService;
import com.realestate.agentsystem.model.Agent;
import com.realestate.agentsystem.model.User;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
public class AgentsystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(AgentsystemApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(AgentService agentService, UserService userService,
			PasswordEncoder passwordEncoder) {
		return args -> {
			// Add admin user if no users exist
			if (userService.getAllUsers().isEmpty()) {
				User adminUser = new User();
				adminUser.setId(UUID.randomUUID().toString());
				adminUser.setName("Admin User");
				adminUser.setEmail("admin@example.com");
				adminUser.setPassword(passwordEncoder.encode("admin123"));
				adminUser.setPhoneNumber("555-123-4567");
				adminUser.setRole("ROLE_ADMIN");
				adminUser.setActive(true);
				adminUser.setRegistrationDate(LocalDateTime.now());
				adminUser.setAddress("Admin Office");
				userService.saveUser(adminUser);

				User regularUser = new User();
				regularUser.setId(UUID.randomUUID().toString());
				regularUser.setName("Regular User");
				regularUser.setEmail("user@example.com");
				regularUser.setPassword(passwordEncoder.encode("user123"));
				regularUser.setPhoneNumber("555-987-6543");
				regularUser.setRole("ROLE_USER");
				regularUser.setActive(true);
				regularUser.setRegistrationDate(LocalDateTime.now());
				regularUser.setAddress("User Home");
				userService.saveUser(regularUser);
			}

			// Add some initial data for testing if needed
			if (agentService.getAllAgents().isEmpty()) {
				// First agent
				Agent agent1 = new Agent();
				agent1.setId(UUID.randomUUID().toString());
				agent1.setName("John Doe");
				agent1.setEmail("john.doe@example.com");
				agent1.setPassword(passwordEncoder.encode("agent123"));
				agent1.setPhoneNumber("123-456-7890");
				agent1.setRole("ROLE_AGENT");
				agent1.setActive(true);
				agent1.setRegistrationDate(LocalDateTime.now());
				agent1.setSpecialization("Residential");
				agent1.setExperience("10 years");
				agent1.setLocation("Downtown");
				agent1.setAverageRating(4.5);
				agentService.saveAgent(agent1);

				// Second agent
				Agent agent2 = new Agent();
				agent2.setId(UUID.randomUUID().toString());
				agent2.setName("Jane Smith");
				agent2.setEmail("jane.smith@example.com");
				agent2.setPassword(passwordEncoder.encode("agent123"));
				agent2.setPhoneNumber("987-654-3210");
				agent2.setRole("ROLE_AGENT");
				agent2.setActive(true);
				agent2.setRegistrationDate(LocalDateTime.now());
				agent2.setSpecialization("Commercial");
				agent2.setExperience("15 years");
				agent2.setLocation("Uptown");
				agent2.setAverageRating(4.8);
				agentService.saveAgent(agent2);
			}
		};
	}
}
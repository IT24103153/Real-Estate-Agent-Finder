package com.example.Real_Estate_Agent_Finder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = {"com.example.Real_Estate_Agent_Finder", "com.example"})
@ServletComponentScan(basePackages = {"com.example.Real_Estate_Agent_Finder", "com.example"})
public class RealEstateAgentFinderApplication {
	public static void main(String[] args) {
		SpringApplication.run(RealEstateAgentFinderApplication.class, args);
	}
}
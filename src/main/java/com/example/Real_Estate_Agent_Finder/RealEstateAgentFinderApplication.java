package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class RealEstateAgentFinderApplication {
	public static void main(String[] args) {
		SpringApplication.run(RealEstateAgentFinderApplication.class, args);
	}
}
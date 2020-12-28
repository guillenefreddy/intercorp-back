package com.intercorp.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intercorp.controller.ClientController;

@RestController
@SpringBootApplication
@ComponentScan(basePackages = "com.intercorp")
public class IntercorpBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntercorpBackApplication.class, args);
	}

	@GetMapping("/")
	public String init() {
		return "Intercorp Challenge";
	}
}

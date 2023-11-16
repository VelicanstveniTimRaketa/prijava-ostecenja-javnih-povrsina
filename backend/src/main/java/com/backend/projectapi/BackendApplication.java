package com.backend.projectapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		System.out.println("#############");
		System.out.println(System.getenv("DB_URL"));
		SpringApplication.run(BackendApplication.class, args);
	}

}

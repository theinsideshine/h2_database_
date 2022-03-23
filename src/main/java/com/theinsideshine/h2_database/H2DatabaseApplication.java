package com.theinsideshine.h2_database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class H2DatabaseApplication {

	public static void main(String[] args) {
		System.out.println("BackEnd-PostGres V2.0.01\n");
		SpringApplication.run(H2DatabaseApplication.class, args);
	}

}

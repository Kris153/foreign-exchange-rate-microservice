package com.example.fxservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class FXServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FXServiceApplication.class, args);
	}

}

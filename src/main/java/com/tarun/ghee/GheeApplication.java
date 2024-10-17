package com.tarun.ghee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GheeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GheeApplication.class, args);
	}

}

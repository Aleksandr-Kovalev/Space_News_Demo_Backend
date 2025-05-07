package com.spacenews.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpaceNewsDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaceNewsDemoApplication.class, args);
	}

}

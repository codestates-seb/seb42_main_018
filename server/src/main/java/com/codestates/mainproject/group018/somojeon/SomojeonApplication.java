package com.codestates.mainproject.group018.somojeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SomojeonApplication {

	public static void main(String[] args) {
		SpringApplication.run(SomojeonApplication.class, args);
	}

}

package com.EChowk.EChowk;

import com.EChowk.EChowk.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableCaching
public class EChowkApplication {

	public static void main(String[] args) {
		SpringApplication.run(EChowkApplication.class, args);
	}

	@Bean
	public CommandLineRunner seedDemoUser(UserService userService) {
		return args -> {
			userService.seedDemoUser();
			System.out.println("✅ Demo user seeded (if not already present).");
		};
	}
}

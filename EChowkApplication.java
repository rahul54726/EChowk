package com.EChowk.EChowk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class EChowkApplication {

	public static void main(String[] args) {
		SpringApplication.run(EChowkApplication.class, args);
	}

}

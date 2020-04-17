package com.mmabas77;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.mmabas77.backend.persistence.repositories")
public class SpringBootCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCourseApplication.class, args);
	}

}

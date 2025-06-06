package com.lazymind.java_in_readme_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JavaInReadmeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaInReadmeBackendApplication.class, args);
//		ApplicationContext context = SpringApplication.run(JavaInReadmeBackendApplication.class, args);
//		context.getBean(PeriodicScheduler.class).processRepo();
	}

}


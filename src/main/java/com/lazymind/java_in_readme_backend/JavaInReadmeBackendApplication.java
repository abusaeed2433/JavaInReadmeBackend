package com.lazymind.java_in_readme_backend;

import com.lazymind.java_in_readme_backend.schedular.PeriodicScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JavaInReadmeBackendApplication {

	private static final Logger log = LoggerFactory.getLogger(JavaInReadmeBackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JavaInReadmeBackendApplication.class, args);
//		ApplicationContext context = SpringApplication.run(JavaInReadmeBackendApplication.class, args);
//		context.getBean(PeriodicScheduler.class).processRepo();
	}

}

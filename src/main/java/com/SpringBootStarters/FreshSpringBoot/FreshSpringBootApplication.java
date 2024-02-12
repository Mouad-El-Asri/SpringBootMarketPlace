package com.SpringBootStarters.FreshSpringBoot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class FreshSpringBootApplication {
	private static final Logger logger = LoggerFactory.getLogger(FreshSpringBootApplication.class);

	public static void main(String[] args) {
		logger.info("Starting FreshSpringBootApplication");
		SpringApplication.run(FreshSpringBootApplication.class, args);
	}
}

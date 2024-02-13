package com.SpringBootStarters.MarketPlace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MarketPlace {
	private static final Logger logger = LoggerFactory.getLogger(MarketPlace.class);

	public static void main(String[] args) {
		logger.info("Starting MarketPlaceApplication");
		SpringApplication.run(MarketPlace.class, args);
	}
}

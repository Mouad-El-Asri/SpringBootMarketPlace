package com.SpringBootStarters.MarketPlace;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class MarketPlace {
	private static final Logger logger = LoggerFactory.getLogger(MarketPlace.class);

	public static void main(String[] args) {
		logger.info("Starting MarketPlaceApplication");
		SpringApplication.run(MarketPlace.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}

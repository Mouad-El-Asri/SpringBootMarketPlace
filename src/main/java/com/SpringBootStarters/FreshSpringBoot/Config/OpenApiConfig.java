package com.SpringBootStarters.FreshSpringBoot.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
	info = @Info(
		contact = @Contact(
			name = "Mouad El Asri"
		),
		description = "OpenApi Documentation for FreshSpringBootApplication Project",
		title = "FreshSpringBootApplication - Mouad El Asri",
		version = "1.0"
	),
	servers = {
		@Server(
			description = "Local Server",
			url = "http://localhost:8080"
		)
	}
)
public class OpenApiConfig {
	
}

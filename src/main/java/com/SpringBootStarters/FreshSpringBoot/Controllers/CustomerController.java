package com.SpringBootStarters.FreshSpringBoot.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootStarters.FreshSpringBoot.Entities.Customer;
import com.SpringBootStarters.FreshSpringBoot.Services.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping(path = "api/v1/customers")
@Tag(name = "Customer", description = "The customer API")
public class CustomerController {
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * Retrieves a list of customers.
	 *
	 * @return the list of customers
	 */
	@GetMapping("/")
	@Operation(
		summary = "Get all customers",
		description = "Get all customers data from the database",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "The list of customers",
				content = @Content(
					mediaType = "application/json",
					examples = {
                        @ExampleObject(
							name = "Customer Example 1",
							value = "{\"id\": 1, \"firstName\": \"John\", \"lastName\": \"Wick\", \"email\": \"contact@gmail.com\", \"age\": 30}"
						)
                    }
				)
			)
		}
	)
	public List<Customer> getCustomers() {
		return this.customerService.getCustomers();
	}

	@PostMapping("/create")
	@Operation(summary = "Create a new customer", description = "Create a new customer in the database")
	public Customer createCustomer(@RequestBody Customer customer) {
		return customer;
	}
}

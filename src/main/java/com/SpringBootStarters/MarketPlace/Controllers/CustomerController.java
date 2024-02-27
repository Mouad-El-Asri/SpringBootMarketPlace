package com.SpringBootStarters.MarketPlace.Controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootStarters.MarketPlace.DTOs.CustomerDto;
import com.SpringBootStarters.MarketPlace.Entities.Customer;
import com.SpringBootStarters.MarketPlace.Services.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping(path = "api/v1/customers")
@Tag(name = "Customer", description = "The customer API")
@Validated
public class CustomerController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * Retrieves a list of customers.
	 * @return The list of customers
	 */
	@GetMapping("/")
	@Operation(summary = "Get all customers", description = "Get all customers data from the database", responses = {
			@ApiResponse(responseCode = "200", description = "The list of customers")
	})
	public ResponseEntity<List<Customer>> getCustomers() {
		logger.info("Getting all customers");
		return ResponseEntity.ok(this.customerService.getCustomers());
	}

	/**
	 * Get customer by id
	 * @param id The customer id
	 * @return The customer instance
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Get customer by id", description = "Get customer data from the database", responses = {
			@ApiResponse(responseCode = "200", description = "The customer")
	})
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") @Positive(message = "Id must be a positive number") long id) {
		logger.info("Getting customer by id");
		return ResponseEntity.ok(this.customerService.getCustomer(id));
	}

	/**
	 * Create a new customer
	 * @param customerDto The DTO containing customer information
	 * @return The new customer
	 */
	@PostMapping("/create")
	@Operation(summary = "Create a new customer", description = "Create a new customer in the database", responses = {
			@ApiResponse(responseCode = "201", description = "The new created customer")
	})
	public ResponseEntity<Customer> createCustomer(@RequestBody @Valid CustomerDto customerDto) {
		logger.info("Creating a new customer");
		return ResponseEntity.status(HttpStatus.CREATED).body(this.customerService.createCustomer(customerDto));
	}

	/**
	 * Update a customer
	 * @param id The customer's id
	 * @param customerDto The customer DTO containing customer information to be updated
	 * @return The updated customer
	 */
	@PutMapping("/update/{id}")
	@Operation(summary = "Update a customer", description = "Update a customer in the database", responses = {
			@ApiResponse(responseCode = "200", description = "The updated customer")
	})
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") @Positive(message = "Id must be a positive number") long id, @RequestBody @Valid CustomerDto customerDto) {
		logger.info("Update customer by id");
		return ResponseEntity.ok(this.customerService.updateCustomer(id, customerDto));
	}

	/**
	 * Delete a customer
	 * @param id The customer's id to be deleted
	 */
	@DeleteMapping("/delete/{id}")
	@Operation(summary = "Delete a customer", description = "Delete a customer from the database", responses = {
			@ApiResponse(responseCode = "200", description = "Customer deleted successfully")
	})
	public ResponseEntity<Void> deleteCustomer(@PathVariable("id") @Positive(message = "Id must be a positive number") long id) {
		logger.info("Delete customer by id");
		this.customerService.deleteCustomer(id);
		return ResponseEntity.ok().build();
	}
}

package com.SpringBootStarters.FreshSpringBoot.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootStarters.FreshSpringBoot.Entities.Customer;
import com.SpringBootStarters.FreshSpringBoot.Services.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "api/v1/customer")
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
	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		return this.customerService.getCustomers();
	}

	@PostMapping("/create")
	public Customer postMethodName(@RequestBody Customer customer) {
		return customer;
	}
	
}

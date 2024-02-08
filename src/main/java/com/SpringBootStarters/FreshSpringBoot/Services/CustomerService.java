package com.SpringBootStarters.FreshSpringBoot.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.SpringBootStarters.FreshSpringBoot.Entities.Customer;

@Service
public class CustomerService {
	/**
	 * Retrieves a list of customers.
	 *
	 * @return the list of customers
	 */
	public List<Customer> getCustomers() {
		return List.of(new Customer(
			1L,
			"Mouad",
			"El Asri",
			"contact@gmail.com",
			21
		));
	}
}

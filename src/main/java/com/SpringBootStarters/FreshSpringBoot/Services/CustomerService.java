package com.SpringBootStarters.FreshSpringBoot.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.SpringBootStarters.FreshSpringBoot.Entities.Customer;
import com.SpringBootStarters.FreshSpringBoot.Repositories.CustomerRepository;

@Service
public class CustomerService {
	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	/**
	 * Retrieves a list of customers.
	 * @return the list of customers
	 */
	public List<Customer> getCustomers() {
		return this.customerRepository.findAll();
	}

	/** Get customer by id
	 * @param id The customer's id
	 * @return The customer instance
	 */
	public Optional<Customer> getCustomer(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Customer id can't be null");
		return this.customerRepository.findById(id);
	}

	/**
	 * Create a new customer
	 * @param customer The customer to create
	 * @return The new customer
	 */
	public void createCustomer(Customer customer) {
		Optional<Customer> customerOptional = this.customerRepository.findByEmail(customer.getEmail());
		if (customerOptional.isPresent()) {
			throw new IllegalStateException("Email already taken");
		}
		this.customerRepository.save(customer);
	}

	/**
	 * Delete a customer by id
	 * @param id The customer's id
	 */
	public void deleteCustomer(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Customer id can't be null");
		boolean exists = this.customerRepository.existsById(id);
		if (!exists)
			throw new IllegalStateException("Customer with id " + id + " doesn't exist");
		this.customerRepository.deleteById(id);
	}

	/**
	 * Update a customer
	 * @param id The customer's id
	 * @param customer The customer to update
	 * @return The updated customer
	 */
	public Customer updateCustomer(Long id, Customer customer) {
		if (customer == null || id == null)
			throw new IllegalArgumentException("Customer and id can't be null");
		Customer existingCustomer = this.customerRepository.findById(id).orElse(null);
		existingCustomer.setFirstName(customer.getFirstName());
		existingCustomer.setLastName(customer.getLastName());
		existingCustomer.setEmail(customer.getEmail());
		existingCustomer.setAge(customer.getAge());
		return this.customerRepository.save(existingCustomer);
	}
}

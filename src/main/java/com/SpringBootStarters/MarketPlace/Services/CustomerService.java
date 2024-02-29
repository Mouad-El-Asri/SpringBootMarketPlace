package com.SpringBootStarters.MarketPlace.Services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.SpringBootStarters.MarketPlace.DTOs.CustomerDto;
import com.SpringBootStarters.MarketPlace.Entities.Customer;
import com.SpringBootStarters.MarketPlace.Repositories.CustomerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CustomerService {
	private final CustomerRepository customerRepository;
	private final ModelMapper modelMapper;

	public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper) {
		this.customerRepository = customerRepository;
		this.modelMapper = modelMapper;
	}

	/**
	 * Retrieves a list of customers.
	 * @return the list of customers
	 */
	public List<Customer> getCustomers() {
		return this.customerRepository.findAll();
	}

	/**
	 * Get customer by id
	 * 
	 * @param id The customer's id
	 * @return The customer instance
	 */
	public Customer getCustomer(long id) {
		return this.customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer with id " + id + " doesn't exist"));
	}

	/**
	 * Create a new customer
	 * 
	 * @param customerDto The DTO containing customer information
	 * @return The new customer
	 */
	public Customer createCustomer(CustomerDto customerDto) {
		if (customerDto == null)
			throw new IllegalArgumentException("Customer can't be null");
		Optional<Customer> customerOptional = this.customerRepository.findByEmail(customerDto.getEmail());
		if (customerOptional.isPresent())
			throw new IllegalStateException("Email already taken");
		Customer customer = modelMapper.map(customerDto, Customer.class);
		return this.customerRepository.save(customer);
	}

	/**
	 * Delete a customer by id
	 * 
	 * @param id The customer's id
	 */
	public void deleteCustomer(long id) {
		boolean exists = this.customerRepository.existsById(id);
		if (!exists)
			throw new EntityNotFoundException("Customer with id " + id + " doesn't exist");
		this.customerRepository.deleteById(id);
	}

	/**
	 * Update a customer
	 * 
	 * @param id          The customer id
	 * @param customerDto The DTO containing customer information
	 * @return The updated customer
	 * @throws IllegalStateException if the customerDto is null
	 */
	public Customer updateCustomer(long id, CustomerDto customerDto) {
		if (customerDto == null)
			throw new IllegalArgumentException("Customer can't be null");
		Optional<Customer> customerOptional = this.customerRepository.findByEmail(customerDto.getEmail());
		if (customerOptional.isPresent())
			throw new IllegalStateException("Email already taken");
		Customer existingCustomer = this.customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer with id " + id + " doesn't exist"));
		modelMapper.map(customerDto, existingCustomer);
		return this.customerRepository.save(existingCustomer);
	}
}

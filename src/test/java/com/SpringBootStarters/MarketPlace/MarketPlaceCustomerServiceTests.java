package com.SpringBootStarters.MarketPlace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.SpringBootStarters.MarketPlace.DTOs.CustomerDto;
import com.SpringBootStarters.MarketPlace.Entities.Customer;
import com.SpringBootStarters.MarketPlace.Repositories.CustomerRepository;
import com.SpringBootStarters.MarketPlace.Services.CustomerService;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
class MarketPlaceCustomerServiceTests {
	private static final Logger logger = LoggerFactory.getLogger(MarketPlaceCustomerServiceTests.class);
	
	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerService customerService;

	@BeforeAll
	public static void setUp() {
		logger.info("Starting customer service tests");
	}

	@Test
	public void testGetCustomers() {
		logger.info("Testing getCustomers method");

		// Testing the retrieval of a list of customers
		List<Customer> customers = Arrays.asList(
			new Customer(1L, "John", "Doe", "john@example.com", 25),
			new Customer(2L,"Jane", "Smith", "jane@example.com", 30)
		);
		Mockito.when(customerRepository.findAll()).thenReturn(customers);
		List<Customer> result = customerService.getCustomers();
		assertEquals(customers, result);

		// Verify
		Mockito.verify(customerRepository, Mockito.times(1)).findAll();
	}

	@Test
	public void testGetCustomer() {
		logger.info("Testing getCustomer method");

		Customer customer = new Customer(1L, "John", "Doe", "john@example.com", 25);

		// Testing the retrieval of an existing customer
		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		Customer result = customerService.getCustomer(1L);
		assertEquals(customer, result);

		// Testing the exception when the customer doesn't exist
		Mockito.when(customerRepository.findById(2L)).thenReturn(Optional.empty());
		EntityNotFoundException thrown = assertThrowsExactly(EntityNotFoundException.class, () -> customerService.getCustomer(2L));
		assertTrue(thrown.getMessage().contains("Customer with id 2 doesn't exist"));

		// Verify
		Mockito.verify(customerRepository, Mockito.times(2)).findById(Mockito.anyLong());
	}

	@Test
	public void testCreateCustomer() {
		logger.info("Testing createCustomer method");
		
		// Testing the creation of a new customer
		CustomerDto customerDto = new CustomerDto("John", "Doe", "john@example.com", 25);
		Customer customer = new Customer(1L, "John", "Doe", "john@example.com", 25);

		Mockito.when(customerRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());
		Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);

		Customer result = customerService.createCustomer(customerDto);

		assertNotNull(result);
		assertEquals(customerDto.getFirstName(), result.getFirstName());
		assertEquals(customerDto.getLastName(), result.getLastName());
		assertEquals(customerDto.getEmail(), result.getEmail());
		assertEquals(customerDto.getAge(), result.getAge());

		// Testing the exception when the customer already exists
		Mockito.when(customerRepository.findByEmail("john@example.com")).thenReturn(Optional.of(customer));
		IllegalStateException thrown = assertThrowsExactly(IllegalStateException.class, () -> customerService.createCustomer(customerDto));
		assertTrue(thrown.getMessage().contains("Email already taken"));

		// Testing the exception when the customerDto is null
		IllegalArgumentException thrown2 = assertThrowsExactly(IllegalArgumentException.class, () -> customerService.createCustomer(null));
		assertTrue(thrown2.getMessage().contains("Customer can't be null"));

		// Verify
		Mockito.verify(customerRepository, Mockito.times(2)).findByEmail(Mockito.anyString());
		Mockito.verify(customerRepository, Mockito.times(1)).save(Mockito.any(Customer.class));
	}

	@Test
	public void testUpdateCustomer() {
		logger.info("Testing updateCustomer method");
		
		CustomerDto customerDto = new CustomerDto("John", "Doe", "doe@example.com", 30);
		Customer customer = new Customer(1L, "John", "Doe", "john@example.com", 25);
		
		// Testing the update of an existing customer
		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);
		
		Customer result = customerService.updateCustomer(1L, customerDto);

		assertNotNull(result);
		assertEquals(customerDto.getFirstName(), result.getFirstName());
		assertEquals(customerDto.getLastName(), result.getLastName());
		assertEquals(customerDto.getEmail(), result.getEmail());
		assertEquals(customerDto.getAge(), result.getAge());

		// Testing the exception when the customer doesn't exist
		Mockito.when(customerRepository.findById(2L)).thenReturn(Optional.empty());
		assertThrowsExactly(EntityNotFoundException.class, () -> customerService.updateCustomer(2L, customerDto));

		// Testing the exception when the customerDto is null
		assertThrowsExactly(IllegalArgumentException.class, () -> customerService.updateCustomer(1L, null));

		// Verify
		Mockito.verify(customerRepository, Mockito.times(2)).findById(Mockito.anyLong());
		Mockito.verify(customerRepository, Mockito.times(1)).save(Mockito.any(Customer.class));
	}

	@Test
	public void testDeleteCustomer() {
		logger.info("Testing deleteCustomer method");

		// Testing the deletion of an existing customer
		Mockito.when(customerRepository.existsById(1L)).thenReturn(true);
		customerService.deleteCustomer(1L);

		// Testing the exception when the customer doesn't exist
		Mockito.when(customerRepository.existsById(2L)).thenReturn(false);
		EntityNotFoundException thrown = assertThrowsExactly(EntityNotFoundException.class, () -> customerService.deleteCustomer(2L));
		assertTrue(thrown.getMessage().contains("Customer with id 2 doesn't exist"));

		// Verify
		Mockito.verify(customerRepository, Mockito.times(1)).deleteById(1L);
		Mockito.verify(customerRepository, Mockito.times(2)).existsById(Mockito.anyLong());
	}

	@AfterAll
	public static void tearDown() {
		logger.info("Finishing customer service tests");
	}
}

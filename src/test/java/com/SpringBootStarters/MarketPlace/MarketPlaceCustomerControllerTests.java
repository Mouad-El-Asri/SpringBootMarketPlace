package com.SpringBootStarters.MarketPlace;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.SpringBootStarters.MarketPlace.Controllers.CustomerController;
import com.SpringBootStarters.MarketPlace.DTOs.CustomerDto;
import com.SpringBootStarters.MarketPlace.Entities.Customer;
import com.SpringBootStarters.MarketPlace.Services.CustomerService;

@SpringBootTest
class MarketPlaceCustomerControllerTests {
	private static final Logger logger = LoggerFactory.getLogger(MarketPlaceCustomerControllerTests.class);
	
	@Mock
	private CustomerService customerService;

	@InjectMocks
	private CustomerController customerController;

	@BeforeAll
	public static void setUp() {
		logger.info("Starting customer controller tests");
	}

	@Test
	public void testGetCustomers() {
		logger.info("Testing getCustomers method");

		// Testing the retrieval of a list of customers
		List<Customer> customers = Arrays.asList(
			new Customer(1L, "John", "Doe", "john@example.com", 25),
			new Customer(2L,"Jane", "Smith", "jane@example.com", 30)
		);

		Mockito.when(customerService.getCustomers()).thenReturn(customers);

		ResponseEntity<List<Customer>> responseEntity = customerController.getCustomers();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(customers, responseEntity.getBody());

		Mockito.verify(customerService, Mockito.times(1)).getCustomers();
	}

	@Test
	public void testGetCustomer() {
		logger.info("Testing getCustomer method");

		// Testing the retrieval of a customer by id
		Customer customer = new Customer(1L,"Jane", "Smith", "jane@example.com", 30);

		Mockito.when(customerService.getCustomer(1L)).thenReturn(customer);
		ResponseEntity<Customer> responseEntity = customerController.getCustomer(1L);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(customer, responseEntity.getBody());

		// Verify
		Mockito.verify(customerService, Mockito.times(1)).getCustomer(1L);
	}

	@Test
	public void testCreateCustomer() {
		logger.info("Testing createCustomer method");

		CustomerDto customerDto = new CustomerDto("John", "Doe", "doe@example.com", 30);
		Customer customer = new Customer(1L, "John", "Doe", "doe@example.com", 30);
		
		// Testing the creation of a new customer
		Mockito.when(customerService.createCustomer(customerDto)).thenReturn(customer);
		ResponseEntity<Customer> responseEntity = customerController.createCustomer(customerDto);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(customer, responseEntity.getBody());

		// Verify
		Mockito.verify(customerService, Mockito.times(1)).createCustomer(customerDto);
	}

	@Test
	public void testUpdateCustomer() {
		logger.info("Testing updateCustomer method");

		CustomerDto customerDto = new CustomerDto("John", "Doe", "doe@example.com", 30);
		Customer customer = new Customer(1L, "John", "Doe", "doe@example.com", 30);

		// Testing the update of an existing customer
		Mockito.when(customerService.updateCustomer(1L, customerDto)).thenReturn(customer);
		ResponseEntity<Customer> responseEntity = customerController.updateCustomer(1L, customerDto);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(customer, responseEntity.getBody());

		// Verify
		Mockito.verify(customerService, Mockito.times(1)).updateCustomer(1L, customerDto);
	}

	@Test
	public void testDeleteCustomer() {
		logger.info("Testing deleteCustomer method");

		// Testing the deletion of an existing customer
		Mockito.doNothing().when(customerService).deleteCustomer(1L);

		ResponseEntity<Void> responseEntity = customerController.deleteCustomer(1L);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(null, responseEntity.getBody());

		// Verify
		Mockito.verify(customerService, Mockito.times(1)).deleteCustomer(Mockito.anyLong());
	}

	@AfterAll
	public static void tearDown() {
		logger.info("Finishing customer controller tests");
	}
}

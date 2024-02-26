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
import com.SpringBootStarters.MarketPlace.Entities.Customer;
import com.SpringBootStarters.MarketPlace.Services.CustomerService;

@SpringBootTest
class MarketPlaceApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(MarketPlaceApplicationTests.class);

	@InjectMocks
	private CustomerController customerController;

	@Mock
	private CustomerService customerService;

	@BeforeAll
	public static void setUp() {
		logger.info("Starting tests");
	}

	@Test
	void testGetCustomers() {
		logger.info("Testing getCustomers method");

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

	// @Test
	// void testGetCustomer() {
	// 	logger.info("Testing getCustomer method");

	// 	Customer customer = new Customer(1L,"Jane", "Smith", "jane@example.com", 30);

	// 	Mockito.when(customerService.getCustomer(1L)).thenReturn(java.util.Optional.of(customer));
	// }

	@AfterAll
	public static void tearDown() {
		logger.info("Finishing tests");
	}
}

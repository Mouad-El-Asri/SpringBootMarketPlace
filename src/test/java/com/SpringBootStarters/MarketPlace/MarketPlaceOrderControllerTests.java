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

import com.SpringBootStarters.MarketPlace.Controllers.OrderController;
import com.SpringBootStarters.MarketPlace.DTOs.OrderDto;
import com.SpringBootStarters.MarketPlace.Entities.Orders;
import com.SpringBootStarters.MarketPlace.Services.OrderService;

@SpringBootTest
public class MarketPlaceOrderControllerTests {
	private static final Logger logger = LoggerFactory.getLogger(MarketPlaceOrderControllerTests.class);

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderController orderController;

	@BeforeAll
	public static void setUp() {
		logger.info("Starting order controller tests");
	}

	@Test
	public void testGetOrders() {
		logger.info("Testing getOrders method");

		// Testing the retrieval of a list of orders
		List<Orders> orders = Arrays.asList(new Orders(1L));
		Mockito.when(orderService.getOrders()).thenReturn(orders);
		ResponseEntity<List<Orders>> responseEntity = orderController.getOrders();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(orders, responseEntity.getBody());

		// Verify
		Mockito.verify(orderService, Mockito.times(1)).getOrders();
	}

	@Test
	public void testGetOrder() {
		logger.info("Testing getOrder method");

		// Testing the retrieval of an order by its ID
		Orders order = new Orders(1L);
		Mockito.when(orderService.getOrder(1L)).thenReturn(order);
		ResponseEntity<Orders> responseEntity = orderController.getOrder(1L);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(order, responseEntity.getBody());

		// Verify
		Mockito.verify(orderService, Mockito.times(1)).getOrder(1L);
	}

	@Test
	public void testGetOrdersByProductId() {
		logger.info("Testing getOrdersByProductId method");

		// Testing the retrieval of a list of orders by product ID
		List<Orders> orders = Arrays.asList(new Orders(1L));
		Mockito.when(orderService.getOrdersByProductId(1L)).thenReturn(orders);
		ResponseEntity<List<Orders>> responseEntity = orderController.getOrdersByProductId(1L);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(orders, responseEntity.getBody());

		// Verify
		Mockito.verify(orderService, Mockito.times(1)).getOrdersByProductId(1L);
	}

	@Test
	public void testGetOrdersByCustomerId() {
		logger.info("Testing getOrdersByCustomerId method");

		// Testing the retrieval of a list of orders by customer ID
		List<Orders> orders = Arrays.asList(new Orders(1L));
		Mockito.when(orderService.getOrdersByCustomerId(1L)).thenReturn(orders);
		ResponseEntity<List<Orders>> responseEntity = orderController.getOrdersByCustomerId(1L);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(orders, responseEntity.getBody());

		// Verify
		Mockito.verify(orderService, Mockito.times(1)).getOrdersByCustomerId(1L);
	}

	@Test
	public void testCreateOrder() {
		logger.info("Testing createOrder method");

		Orders order = new Orders(1L);
		OrderDto orderDto = new OrderDto();

		// Testing the creation of an order
		Mockito.when(orderService.createOrder(1L, orderDto)).thenReturn(order);
		ResponseEntity<Orders> responseEntity = orderController.createOrder(1L, orderDto);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(order, responseEntity.getBody());

		// Verify
		Mockito.verify(orderService, Mockito.times(1)).createOrder(1L, orderDto);
	}

	@Test
	public void testUpdateOrder() {
		logger.info("Testing updateOrder method");

		Orders order = new Orders(1L);
		OrderDto orderDto = new OrderDto();

		// Testing the update of an order
		Mockito.when(orderService.updateOrder(1L, orderDto)).thenReturn(order);
		ResponseEntity<Orders> responseEntity = orderController.updateOrders(1L, orderDto);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(order, responseEntity.getBody());

		// Verify
		Mockito.verify(orderService, Mockito.times(1)).updateOrder(1L, orderDto);
	}

	@Test
	public void testDeleteOrder() {
		logger.info("Testing deleteOrder method");

		// Testing the deletion of an order by its ID
		Mockito.doNothing().when(orderService).deleteOrder(1L);
		ResponseEntity<Void> responseEntity = orderController.deleteOrder(1L);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		// Verify
		Mockito.verify(orderService, Mockito.times(1)).deleteOrder(1L);
	}

	@AfterAll
	public static void tearDown() {
		logger.info("Finished order controller tests");
	}
}

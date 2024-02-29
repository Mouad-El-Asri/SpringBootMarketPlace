package com.SpringBootStarters.MarketPlace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
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

import com.SpringBootStarters.MarketPlace.DTOs.OrderDto;
import com.SpringBootStarters.MarketPlace.Entities.Customer;
import com.SpringBootStarters.MarketPlace.Entities.Orders;
import com.SpringBootStarters.MarketPlace.Entities.Product;
import com.SpringBootStarters.MarketPlace.Repositories.CustomerRepository;
import com.SpringBootStarters.MarketPlace.Repositories.OrderRepository;
import com.SpringBootStarters.MarketPlace.Repositories.ProductRepository;
import com.SpringBootStarters.MarketPlace.Services.OrderService;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class MarketPlaceOrderServiceTests {
	private static final Logger logger = LoggerFactory.getLogger(MarketPlaceCustomerControllerTests.class);
	
	@Mock
	private OrderRepository orderRepository;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private OrderService orderService;

	@BeforeAll
	public static void setUp() {
		logger.info("Starting customer controller tests");
	}

	@Test
	public void testGetOrders() {
		logger.info("Testing getOrders method");

		// Testing the retrieval of a list of orders
		List<Orders> orders = Arrays.asList(new Orders(1L));
		Mockito.when(orderRepository.findAll()).thenReturn(orders);
		List<Orders> result = orderService.getOrders();
		assertEquals(orders, result);

		// Verify
		Mockito.verify(orderRepository, Mockito.times(1)).findAll();
	}

	@Test
	public void testGetOrder() {
		logger.info("Testing getOrder method");

		// Testing the retrieval of an order by its ID
		Orders order = new Orders(1L);
		Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
		Orders result = orderService.getOrder(1L);
		assertEquals(order, result);

		// Testing the retrieval of an order that does not exist
		Mockito.when(orderRepository.findById(2L)).thenReturn(Optional.empty());
		EntityNotFoundException thrown = assertThrowsExactly(EntityNotFoundException.class, () -> orderService.getOrder(2L));
		assertTrue(thrown.getMessage().contains("Order not found with Id : 2"));

		// Verify
		Mockito.verify(orderRepository, Mockito.times(1)).findById(1L);
	}

	@Test
	public void testGetOrdersByProductId() {
		logger.info("Testing getOrdersByProductId method");

		// Testing the retrieval of a list of orders for a product
		List<Orders> orders = Arrays.asList(new Orders(1L));
		Mockito.when(orderRepository.findByProductsId(1L)).thenReturn(orders);
		List<Orders> result = orderService.getOrdersByProductId(1L);
		assertEquals(orders, result);

		// Testing the retrieval of a list of orders for a product that does not exist
		Mockito.when(orderRepository.findByProductsId(2L)).thenReturn(Arrays.asList());
		EntityNotFoundException thrown = assertThrowsExactly(EntityNotFoundException.class, () -> orderService.getOrdersByProductId(2L));
		assertTrue(thrown.getMessage().contains("No orders found for product with id 2"));

		// Verify
		Mockito.verify(orderRepository, Mockito.times(1)).findByProductsId(1L);
	}

	@Test
	public void testGetOrdersByCustomerId() {
		logger.info("Testing getOrdersByCustomerId method");

		// Testing the retrieval of a list of orders for a customer
		List<Orders> orders = Arrays.asList(new Orders(1L));
		Mockito.when(orderRepository.findByCustomerId(1L)).thenReturn(orders);
		List<Orders> result = orderService.getOrdersByCustomerId(1L);
		assertEquals(orders, result);

		// Testing the retrieval of a list of orders for a customer that does not exist
		Mockito.when(orderRepository.findByCustomerId(2L)).thenReturn(Arrays.asList());
		EntityNotFoundException thrown = assertThrowsExactly(EntityNotFoundException.class, () -> orderService.getOrdersByCustomerId(2L));
		assertTrue(thrown.getMessage().contains("No orders found for customer with id 2"));

		// Verify
		Mockito.verify(orderRepository, Mockito.times(1)).findByCustomerId(1L);
	}

	@Test
	public void testCreateOrder() {
		logger.info("Testing createOrder method");

		Orders order = new Orders(1L);
		OrderDto orderDto = new OrderDto(Arrays.asList(1L, 2L));
		Customer customer = new Customer(1L, "John", "Doe", "doe@example.com", 25);
		List<Product> products = Arrays.asList(new Product(1L, "Nike Air Jordan 1 Low", BigDecimal.valueOf(1500)), new Product(2L, "Nike Air Jordan 2 High", BigDecimal.valueOf(1700)));
		
		// Testing the creation of a new order
		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		Mockito.when(productRepository.findAllById(orderDto.getProductIds())).thenReturn(products);
		Mockito.when(orderRepository.save(Mockito.any(Orders.class))).thenReturn(order);
		Orders result = orderService.createOrder(1L, orderDto);
		assertEquals(order, result);

		// Testing the creation of a new order with a null orderDto
		IllegalArgumentException thrown = assertThrowsExactly(IllegalArgumentException.class, () -> orderService.createOrder(1L, null));
		assertTrue(thrown.getMessage().contains("Order can't be null"));

		// Testing the creation of a new order with a customer that does not exist
		Mockito.when(customerRepository.findById(2L)).thenReturn(Optional.empty());
		EntityNotFoundException thrown2 = assertThrowsExactly(EntityNotFoundException.class, () -> orderService.createOrder(2L, orderDto));
		assertTrue(thrown2.getMessage().contains("Customer not found with Id : 2"));

		// Verify
		Mockito.verify(customerRepository, Mockito.times(2)).findById(Mockito.anyLong());
		Mockito.verify(productRepository, Mockito.times(1)).findAllById(Mockito.anyList());
		Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(Orders.class));
	}

	@Test
	public void testUpdateOrder() {
		logger.info("Testing updateOrder method");

		Orders order = new Orders(1L);
		OrderDto orderDto = new OrderDto(Arrays.asList(1L, 2L));
		List<Product> products = Arrays.asList(new Product(1L, "Nike Air Jordan 1 Low", BigDecimal.valueOf(1500)), new Product(2L, "Nike Air Jordan 2 High", BigDecimal.valueOf(1700)));

		// Testing the update of an order
		Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
		Mockito.when(productRepository.findAllById(orderDto.getProductIds())).thenReturn(products);
		Mockito.when(orderRepository.save(Mockito.any(Orders.class))).thenReturn(order);
		Orders result = orderService.updateOrder(1L, orderDto);
		assertEquals(order, result);

		// Testing the update of an order that does not exist
		Mockito.when(orderRepository.findById(2L)).thenReturn(Optional.empty());
		EntityNotFoundException thrown1 = assertThrowsExactly(EntityNotFoundException.class, () -> orderService.updateOrder(2L, orderDto));
		assertTrue(thrown1.getMessage().contains("Order not found with Id : 2"));

		// Testing the update of an order with a null orderDto
		IllegalArgumentException thrown2 = assertThrowsExactly(IllegalArgumentException.class, () -> orderService.updateOrder(1L, null));
		assertTrue(thrown2.getMessage().contains("Order can't be null"));

		// Verify
		Mockito.verify(orderRepository, Mockito.times(2)).findById(Mockito.anyLong());
		Mockito.verify(productRepository, Mockito.times(1)).findAllById(Mockito.anyList());
		Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(Orders.class));
	}

	@Test
	public void testDeleteOrder() {
		logger.info("Testing deleteOrder method");

		// Testing the deletion of an order
		Mockito.when(orderRepository.existsById(1L)).thenReturn(true);
		orderService.deleteOrder(1L);

		// Testing the exception when the order does not exist
		Mockito.when(orderRepository.existsById(2L)).thenReturn(false);
		EntityNotFoundException thrown = assertThrowsExactly(EntityNotFoundException.class, () -> orderService.deleteOrder(2L));
		assertTrue(thrown.getMessage().contains("Order not found with Id : 2"));

		// Verify
		Mockito.verify(orderRepository, Mockito.times(1)).deleteById(1L);
		Mockito.verify(orderRepository, Mockito.times(1)).existsById(2L);
	}

	@AfterAll
	public static void tearDown() {
		logger.info("Finished customer controller tests");
	}
}

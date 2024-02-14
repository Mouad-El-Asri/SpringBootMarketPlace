package com.SpringBootStarters.MarketPlace.Controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootStarters.MarketPlace.Entities.Orders;
import com.SpringBootStarters.MarketPlace.Services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "api/v1/orders")
@Tag(name = "Order", description = "The order API")
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * Retrieves a list of orders from the database.
	 * @return List of orders
	 */
	@GetMapping("/")
	@Operation(summary = "Get a list of orders", description = "Get a list of all the orders from the database", responses = {
			@ApiResponse(responseCode = "200", description = "List of all orders")
	})
	public List<Orders> getOrders() {
		logger.info("Getting all orders");
		return this.orderService.getOrders();
	}

	/**
	 * Retrieves an order from the database based on its id.
	 * 
	 * @param id The id of the order to retrieve.
	 * @return An Optional containing the order if found, or an empty Optional if
	 *         not found.
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Get order by its id", description = "Get order using its id from the database", responses = {
			@ApiResponse(responseCode = "200", description = "Order data returned")
	})
	public Optional<Orders> getOrder(@PathVariable("id") long id) {
		logger.info("Getting a single order by its id");
		return this.orderService.getOrder(id);
	}


	/**
	 * Creates a new order and saves it in the database.
	 * @param id the ID of the customer placing the order
	 * @return the created order
	 */
	@PostMapping("/createOrder/{id}")
	@Operation(summary = "Create new order for a certain customer", description = "Create a new order for a customer and save it in the database", responses = {
			@ApiResponse(responseCode = "201", description = "Order created successfully")
	})
	public Orders createOrder(@PathVariable("id") long id) {
		logger.info("Create new order for customer with id: " + id);
		return this.orderService.createOrder(id);
	}


	/**
	 * Delete an order by id from the database.
	 * @param id The id of the order to be deleted.
	 */
	@DeleteMapping("/delete/{id}")
	@Operation(summary = "Delete an order", description = "Delete an order by id from the database", responses = {
			@ApiResponse(responseCode = "200", description = "Order removed successfully")
	})
	public void deleteOrder(@PathVariable("id") long id) {
		logger.info("Delete an order using its id");
		this.orderService.deleteOrder(id);
	}
}

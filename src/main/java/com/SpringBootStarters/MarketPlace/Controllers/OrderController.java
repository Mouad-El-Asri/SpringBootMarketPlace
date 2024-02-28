package com.SpringBootStarters.MarketPlace.Controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootStarters.MarketPlace.DTOs.OrderDto;
import com.SpringBootStarters.MarketPlace.Entities.Orders;
import com.SpringBootStarters.MarketPlace.Services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping(path = "api/v1/orders")
@Tag(name = "Order", description = "The order API")
@Validated
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
	public ResponseEntity<List<Orders>> getOrders() {
		logger.info("Getting all orders");
		return ResponseEntity.ok(this.orderService.getOrders());
	}

	/**
	 * Retrieves an order from the database based on its id.
	 * @param id The id of the order to retrieve.
	 * @return An Optional containing the order if found, or an empty Optional if
	 *         not found.
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Get order by its id", description = "Get order using its id from the database", responses = {
			@ApiResponse(responseCode = "200", description = "Order data returned")
	})
	public ResponseEntity<Orders> getOrder(@PathVariable("id") @Positive(message = "Id must be a positive number") long id) {
		logger.info("Getting a single order by its id");
		return ResponseEntity.ok(this.orderService.getOrder(id));
	}

	/**
	 * Retrieves a list of orders by product id.
	 * @param id The id of the product
	 * @return A list of orders
	 */
	@GetMapping("/product/{id}")
	@Operation(summary = "Get orders by product id", description = "Get a list of orders by product id", responses = {
			@ApiResponse(responseCode = "200", description = "List of orders")
	})
	public ResponseEntity<List<Orders>> getOrdersByProductId(@PathVariable("id") @Positive(message = "Id must be a positive number") long id) {
		logger.info("Getting a list of orders by product id");
		return ResponseEntity.ok(this.orderService.getOrdersByProductId(id));
	}

	/**
	 * Retrieves a list of orders by customer id.
	 * @param id The id of the customer
	 * @return A list of orders
	 */
	@GetMapping("/customer/{id}")
	@Operation(summary = "Get orders by customer id", description = "Get a list of orders by customer id", responses = {
			@ApiResponse(responseCode = "200", description = "List of orders")
	})
	public ResponseEntity<List<Orders>> getOrdersByCustomerId(@PathVariable("id") @Positive(message = "Id must be a positive number") long id) {
		logger.info("Getting a list of orders by customer id");
		return ResponseEntity.ok(this.orderService.getOrdersByCustomerId(id));
	}

	/**
	 * Creates a new order and saves it in the database.
	 * @param id the ID of the customer placing the order
	 * @param orderDto the order data
	 * @return the created order
	 */
	@PostMapping("/createOrder/{id}")
	@Operation(summary = "Create new order for a certain customer", description = "Create a new order for a customer and save it in the database", responses = {
			@ApiResponse(responseCode = "201", description = "Order created successfully")
	})
	public ResponseEntity<Orders> createOrder(@PathVariable("id") @Positive(message = "Id must be a positive number") long id, @RequestBody @Valid OrderDto orderDto) {
		logger.info("Create new order for customer with id: " + id);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.orderService.createOrder(id, orderDto));
	}

	/**
	 * Update an order by id from the database
	 * @param id The id of the order to be updated
	 * @param orderDto The order dto containing products to be added to the order
	 * @return The updated order
	 */
	@PutMapping("/updateOrder/{id}")
	@Operation(summary = "Update an order", description = "Update an order by id from the database", responses = {
			@ApiResponse(responseCode = "200", description = "Order updated successfully")
	})
	public ResponseEntity<Orders> updateOrders(@PathVariable("id") @Positive(message = "Id must be a positive number") long id, @RequestBody @Valid OrderDto orderDto) {
		logger.info("Adding new products to an exsiting order");
		return ResponseEntity.ok(this.orderService.updateOrder(id, orderDto));
	}

	/**
	 * Delete an order by id from the database.
	 * @param id The id of the order to be deleted.
	 */
	@DeleteMapping("/delete/{id}")
	@Operation(summary = "Delete an order", description = "Delete an order by id from the database", responses = {
			@ApiResponse(responseCode = "200", description = "Order removed successfully")
	})
	public ResponseEntity<Void> deleteOrder(@PathVariable("id") @Positive(message = "Id must be a positive number") long id) {
		logger.info("Delete an order using its id");
		this.orderService.deleteOrder(id);
		return ResponseEntity.ok().build();
	}
}

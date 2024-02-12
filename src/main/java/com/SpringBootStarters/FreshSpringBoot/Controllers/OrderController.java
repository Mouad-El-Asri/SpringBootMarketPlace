package com.SpringBootStarters.FreshSpringBoot.Controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootStarters.FreshSpringBoot.DTOs.OrderDto;
import com.SpringBootStarters.FreshSpringBoot.Entities.Order;
import com.SpringBootStarters.FreshSpringBoot.Services.OrderService;

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
	@Operation(
		summary = "Get a list of orders",
		description = "Get a list of all the orders from the database",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "List of all orders"
			)
		}
	)
	public List<Order> getOrders() {
		logger.info("Getting all orders");
		return this.orderService.getOrders();
	}


	/**
	 * Retrieves an order from the database based on its id.
	 * @param id The id of the order to retrieve.
	 * @return An Optional containing the order if found, or an empty Optional if not found.
	 */
	@GetMapping("/{id}")
	@Operation(
		summary = "Get order by its id",
		description = "Get order using its id from the database",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "Order data returned"
			)
		}
	)
	public Optional<Order> getOrder(@PathVariable("id") long id) {
		logger.info("Getting a single order by its id");
		return this.orderService.getOrder(id);
	}


	@PostMapping("/create")
	@Operation(
		summary = "Create new order",
		description = "Create a new order and save it in the database",
		responses = {
			@ApiResponse(
				responseCode = "201",
				description = "Order created successfully"
			)
		}
	)
	public Order createOrder(@RequestBody OrderDto orderDto) {
		logger.info("Create new order");
		return this.orderService.createOrder(orderDto);
	}


	/**
	 * Updates an order by its id and saves it in the database
	 * @param id The id of the order to be updated
	 * @param orderDto The updated order DTO
	 * @return The updated order
	 */
	@PutMapping("/update/{id}")
	@Operation(
		summary = "Update an order by its id",
		description = "Update an order by id and save it in the database",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "Order updated successfully"
			)
		}
	)
	public Order updateOrder(@PathVariable("id") long id, @RequestBody OrderDto orderDto) {
		logger.info("Update an existing order");
		return this.orderService.updateOrder(id, orderDto);
	}


	/**
	 * Delete an order by id from the database.
	 * @param id The id of the order to be deleted.
	 */
	@DeleteMapping("/delete/{id}")
	@Operation(
		summary = "Delete an order",
		description = "Delete an order by id from the database",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "Order removed successfully"
			)
		}
	)
	public void deleteOrder(@PathVariable("id") long id) {
		logger.info("Delete an order using its id");
		this.orderService.deleteOrder(id);
	}
}
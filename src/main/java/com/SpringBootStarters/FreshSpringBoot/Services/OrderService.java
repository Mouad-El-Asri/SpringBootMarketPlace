package com.SpringBootStarters.FreshSpringBoot.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.SpringBootStarters.FreshSpringBoot.DTOs.OrderDto;
import com.SpringBootStarters.FreshSpringBoot.Entities.Order;
import com.SpringBootStarters.FreshSpringBoot.Repositories.OrderRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}


	/**
	 * Retrieves a list of all orders.
	 * @return A list of Order objects representing all the orders.
	 */
	public List<Order> getOrders() {
		return this.orderRepository.findAll();
	}


	/**
	 * Retrieves the order with the specified ID.
	 * @param id The ID of the order to retrieve
	 * @return An Optional containing the order, or an empty Optional if no order is found
	 */
	public Optional<Order> getOrder(long id) {
		return this.orderRepository.findById(id);
	}


	/**
	 * Creates a new order based on the provided order data.
	 * @param orderDto The order data used to create the order
	 * @return The created order
	 */
	public Order createOrder(OrderDto orderDto) {
		Order newOrder = new Order(orderDto);
		return this.orderRepository.save(newOrder);
	}


	/**
	 * Updates an existing order with the provided order details.
	 * @param id The ID of the order to be updated.
	 * @param orderDto The updated order details.
	 * @return The updated order.
	 * @throws IllegalStateException if the orderDto is null.
	 */
	public Order updateOrder(long id, OrderDto orderDto) {
		if (orderDto == null)
			throw new IllegalStateException("Order can't be null");
		Order exsitngOrder = this.orderRepository.findById(id).orElse(null);
		exsitngOrder.setTotalAmount(orderDto.getTotalAmount());
		return this.orderRepository.save(exsitngOrder);
	}


	/**
	 * Deletes an order with the specified ID.
	 * @param id The ID of the order to delete
	 */
	public void deleteOrder(long id) {
		this.orderRepository.deleteById(id);
	}
}

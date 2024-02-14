package com.SpringBootStarters.MarketPlace.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.SpringBootStarters.MarketPlace.DTOs.OrderDto;
import com.SpringBootStarters.MarketPlace.Entities.Customer;
import com.SpringBootStarters.MarketPlace.Entities.Orders;
import com.SpringBootStarters.MarketPlace.Repositories.CustomerRepository;
import com.SpringBootStarters.MarketPlace.Repositories.OrderRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;
	private final CustomerRepository customerRepository;

	public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
	}

	/**
	 * Retrieves a list of all orders.
	 * @return A list of Order objects representing all the orders.
	 */
	public List<Orders> getOrders() {
		return this.orderRepository.findAll();
	}

	/**
	 * Retrieves the order with the specified ID.
	 * @param id The ID of the order to retrieve
	 * @return An Optional containing the order, or an empty Optional if no order is
	 *         found
	 */
	public Optional<Orders> getOrder(long id) {
		return this.orderRepository.findById(id);
	}

	/**
	 * Creates a new order based on the provided order data.
	 * @param customerId The customer Id
	 * @param orderDto The order data used to create the order
	 * @return The created order
	 */
	public Orders createOrder(long customerId, OrderDto orderDto) {
		Optional<Customer> customerOptional = this.customerRepository.findById(customerId);
		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();
			Orders newOrder = new Orders(orderDto);
			newOrder.setCustomer(customer);
			return this.orderRepository.save(newOrder);
		} else {
			throw new IllegalStateException("Customer not found with Id : " + customerId);
		}
	}

	/**
	 * Updates an existing order with the provided order details.
	 * 
	 * @param id       The ID of the order to be updated.
	 * @param orderDto The updated order details.
	 * @return The updated order.
	 * @throws IllegalStateException if the orderDto is null.
	 */
	public Orders updateOrder(long id, OrderDto orderDto) {
		if (orderDto == null)
			throw new IllegalStateException("Order can't be null");
		Orders exsitngOrder = this.orderRepository.findById(id).orElse(null);
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

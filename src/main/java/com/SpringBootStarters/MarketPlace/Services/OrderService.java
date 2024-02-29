package com.SpringBootStarters.MarketPlace.Services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.SpringBootStarters.MarketPlace.DTOs.OrderDto;
import com.SpringBootStarters.MarketPlace.Entities.Customer;
import com.SpringBootStarters.MarketPlace.Entities.Orders;
import com.SpringBootStarters.MarketPlace.Entities.Product;
import com.SpringBootStarters.MarketPlace.Repositories.CustomerRepository;
import com.SpringBootStarters.MarketPlace.Repositories.OrderRepository;
import com.SpringBootStarters.MarketPlace.Repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {
	private final OrderRepository orderRepository;
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepository;

	public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
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
	public Orders getOrder(long id) {
		return this.orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found with Id : " + id));
	}

	/**
	 * Retrieves a list of orders for a given product.
	 * @param id The ID of the product
	 * @return A list of Order objects representing the orders for the product
	 */
	public List<Orders> getOrdersByProductId(long id) {
		List<Orders> orders = this.orderRepository.findByProductsId(id);
		if (orders.isEmpty())
			throw new EntityNotFoundException("No orders found for product with id " + id);
		return orders;
	}

	/**
	 * Retrieves a list of orders for a given customer.
	 * @param id The ID of the customer
	 * @return A list of Order objects representing the orders for the customer
	 */
	public List<Orders> getOrdersByCustomerId(long id) {
		List<Orders> orders = this.orderRepository.findByCustomerId(id);
		if (orders.isEmpty())
			throw new EntityNotFoundException("No orders found for customer with id " + id);
		return orders;
	}

	/**
	 * Creates a new order based on the provided order data.
	 * @param customerId The customer Id
	 * @param orderDto The order data
	 * @return The created order
	 */
	public Orders createOrder(long customerId, OrderDto orderDto) {
		if (orderDto == null)
			throw new IllegalArgumentException("Order can't be null");
		Customer customer = this.customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found with Id : " + customerId));
		List<Product> products = this.productRepository.findAllById(orderDto.getProductIds());
		Orders newOrder = new Orders();
		newOrder.setCustomer(customer);
		BigDecimal totalAmount = BigDecimal.ZERO;
		for (Product product : products) {
			newOrder.getProducts().add(product);
			totalAmount = totalAmount.add(product.getPrice());
		}
		newOrder.setTotalAmount(totalAmount);
		return this.orderRepository.save(newOrder);
	}

	/**
	 * Updates an order with the specified ID.
	 * @param id The ID of the order to update
	 * @param orderDto The order dto containing products to be added to the order
	 * @return The updated order
	 */
	public Orders updateOrder(long id, OrderDto orderDto) {
		if (orderDto == null)
			throw new IllegalArgumentException("Order can't be null");
		Orders existingOrder = this.orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found with Id : " + id));
		List<Product> products = this.productRepository.findAllById(orderDto.getProductIds());
		BigDecimal totalAmount = existingOrder.getTotalAmount();
		for (Product product : products) {
			if (!existingOrder.getProducts().contains(product)) {
				existingOrder.getProducts().add(product);
				totalAmount = totalAmount.add(product.getPrice());
			}
		}
		existingOrder.setTotalAmount(totalAmount);
		return this.orderRepository.save(existingOrder);
	}

	/**
	 * Deletes an order with the specified ID.
	 * @param id The ID of the order to delete
	 */
	public void deleteOrder(long id) {
		boolean exists = this.orderRepository.existsById(id);
		if (!exists)
			throw new EntityNotFoundException("Order not found with Id : " + id);
		this.orderRepository.deleteById(id);
	}
}

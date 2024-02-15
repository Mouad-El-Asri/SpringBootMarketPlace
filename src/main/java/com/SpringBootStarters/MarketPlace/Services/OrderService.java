package com.SpringBootStarters.MarketPlace.Services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.SpringBootStarters.MarketPlace.DTOs.OrderDto;
import com.SpringBootStarters.MarketPlace.Entities.Customer;
import com.SpringBootStarters.MarketPlace.Entities.Orders;
import com.SpringBootStarters.MarketPlace.Entities.Product;
import com.SpringBootStarters.MarketPlace.Repositories.CustomerRepository;
import com.SpringBootStarters.MarketPlace.Repositories.OrderRepository;
import com.SpringBootStarters.MarketPlace.Repositories.ProductRepository;

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
	public Optional<Orders> getOrder(long id) {
		return this.orderRepository.findById(id);
	}

	/**
	 * Retrieves a list of orders for a given product.
	 * @param id The ID of the product
	 * @return A list of Order objects representing the orders for the product
	 */
	public List<Orders> getOrdersByProductId(long id) {
		return this.orderRepository.findByProductsId(id);
	}

	/**
	 * Creates a new order based on the provided order data.
	 * @param customerId The customer Id
	 * @param orderDto The order data
	 * @return The created order
	 */
	public Orders createOrder(long customerId, OrderDto orderDto) {
		Optional<Customer> customerOptional = this.customerRepository.findById(customerId);
		List<Product> products = this.productRepository.findAllById(orderDto.getProductIds());
		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();
			Orders newOrder = new Orders();
			newOrder.setCustomer(customer);
			BigDecimal totalAmount = BigDecimal.ZERO;
			for (Product product : products) {
				newOrder.getProducts().add(product);
				totalAmount = totalAmount.add(product.getPrice());
			}
			newOrder.setTotalAmount(totalAmount);
			return this.orderRepository.save(newOrder);
		} else {
			throw new IllegalStateException("Customer not found with Id : " + customerId);
		}
	}

	/**
	 * Updates an order with the specified ID.
	 * @param id The ID of the order to update
	 * @param orderDto The order dto containing products to be added to the order
	 * @return The updated order
	 */
	public Orders updateOrder(long id, OrderDto orderDto) {
		Optional<Orders> orderOptional = this.orderRepository.findById(id);
		List<Product> products = this.productRepository.findAllById(orderDto.getProductIds());
		if (orderOptional.isPresent()) {
			Orders existingOrder = orderOptional.get();
			BigDecimal totalAmount = existingOrder.getTotalAmount();
			for (Product product : products) {
				if (!existingOrder.getProducts().contains(product)) {
					existingOrder.getProducts().add(product);
					totalAmount = totalAmount.add(product.getPrice());
				}
			}
			existingOrder.setTotalAmount(totalAmount);
			return this.orderRepository.save(existingOrder);
		} else {
			throw new IllegalStateException("Order not found with Id : " + id);
		}
	}

	/**
	 * Deletes an order with the specified ID.
	 * @param id The ID of the order to delete
	 */
	public void deleteOrder(long id) {
		this.orderRepository.deleteById(id);
	}

	/**
	 * Retrieves a list of orders for a given customer.
	 * @param id The ID of the customer
	 * @return A list of Order objects representing the orders for the customer
	 */
	public List<Orders> getOrdersByCustomerId(long id) {
		return this.orderRepository.findByCustomerId(id);
	}
}

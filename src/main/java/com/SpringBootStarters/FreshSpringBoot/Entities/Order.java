package com.SpringBootStarters.FreshSpringBoot.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.SpringBootStarters.FreshSpringBoot.DTOs.OrderDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an order in the system.
 */
@Setter
@Getter
@Entity
@Table(name = "Order")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "Id must not be null")
	@Min(value = 1, message = "Order id must at least be 1")
	private long id;

	private LocalDateTime date;

	@NotNull(message = "Order total amount must not be null")
	private BigDecimal totalAmount;

	/**
	 * Constructs a new Order object with the current date and time
	 */
	public Order() {
		this.date = LocalDateTime.now();
	}

	/**
	 * Constructs a new Order object based on the provided OrderDto
	 * @param orderDto The OrderDto object containing the order details
	 */
	public Order(OrderDto orderDto) {
		this.date = LocalDateTime.now();
		this.totalAmount = orderDto.getTotalAmount();
	}
}

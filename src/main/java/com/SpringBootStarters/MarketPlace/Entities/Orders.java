package com.SpringBootStarters.MarketPlace.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.SpringBootStarters.MarketPlace.DTOs.OrderDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Orders")
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "Id must not be null")
	@Min(value = 1, message = "Order id must at least be 1")
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	@Column(name = "date", updatable = false, nullable = false)
	private LocalDateTime date;

	@NotNull(message = "Order total amount must not be null")
	@Column(name = "total_amount", precision = 10, scale = 2)
	private BigDecimal totalAmount;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	/**
	 * Constructs a new Order object with the current date and time
	 */
	public Orders() {
		this.date = LocalDateTime.now();
	}

	/**
	 * Constructs a new Order object based on the provided OrderDto
	 * @param orderDto The OrderDto object containing the order details
	 */
	public Orders(OrderDto orderDto) {
		this.date = LocalDateTime.now();
		this.totalAmount = orderDto.getTotalAmount();
	}
}

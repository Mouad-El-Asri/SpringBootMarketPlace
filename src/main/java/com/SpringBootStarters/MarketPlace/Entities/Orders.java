package com.SpringBootStarters.MarketPlace.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	@Column(name = "date", updatable = false, nullable = false)
	private LocalDateTime date;

	@NotNull(message = "Order total amount must not be null")
	@Column(name = "total_amount", precision = 10, scale = 2)
	private BigDecimal totalAmount = BigDecimal.ZERO;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "order_product",
		joinColumns = @JoinColumn(name = "order_id"),
		inverseJoinColumns = @JoinColumn(name = "product_id")
	)
	private List<Product> products = new ArrayList<>();

	public Orders(long id) {
		this.id = id;
	}

	/**
	 * Constructs a new Order object with the current date and time
	 */
	public Orders() {
		this.date = LocalDateTime.now();
	}
}

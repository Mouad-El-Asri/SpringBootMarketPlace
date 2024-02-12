package com.SpringBootStarters.FreshSpringBoot.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a product in the system.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotBlank(message = "Product id must not be blank")
	@Min(value = 1, message = "Product id must be at least 1")
	private long id;

	@NotBlank(message = "Product name must not be blank")
	private String productName;

	@Positive(message = "Price must be a positive number")
	private long price;

	/**
	 * Constructs a new Product object with the specified properties
	 * @param productName The Product name
	 * @param price The Product price
	 */
	public Product(String productName, long price) {
		this.productName = productName;
		this.price = price;
	}
}

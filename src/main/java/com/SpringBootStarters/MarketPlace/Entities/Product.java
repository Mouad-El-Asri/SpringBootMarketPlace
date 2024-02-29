package com.SpringBootStarters.MarketPlace.Entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.SpringBootStarters.MarketPlace.DTOs.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a product in the system.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	@NotBlank(message = "Product name must not be blank")
	@Size(min = 1, max = 100, message = "Product name must be between 1 and 100 characters")
	@Column(name = "product_name", nullable = false, length = 100)
	private String productName;

	@Positive(message = "Price must be a positive number")
	@Column(name = "price", precision = 10, scale = 2)
	private BigDecimal price;

	@ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Orders> orders = new ArrayList<>();

	/**
	 * Constructs a new Product object.
	 * 
	 * @param id         The unique identifier of the product.
	 * @param productName The name of the product.
	 * @param price      The price of the product.
	 */
	public Product(
			@Positive(message = "Id must be a positive number") long id,
			@NotBlank(message = "Product name must not be blank") @Size(min = 1, max = 100, message = "Product name must be between 1 and 100 characters") String productName,
			@Positive(message = "Price must be a positive number") BigDecimal price) {
		this.id = id;
		this.productName = productName;
		this.price = price;
	}
}

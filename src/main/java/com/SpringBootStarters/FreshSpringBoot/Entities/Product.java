package com.SpringBootStarters.FreshSpringBoot.Entities;

import com.SpringBootStarters.FreshSpringBoot.DTOs.ProductDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
	@NotNull(message = "Product id must not be null")
	@Min(value = 1, message = "Product id must be at least 1")
	private long id;

	@NotBlank(message = "Product name must not be blank")
	private String productName;

	@Positive(message = "Price must be a positive number")
	private long price;

	/**
	 * Constructs a new Product object based on the provided ProductDto.
	 * @param productDto The ProductDto object containing the Product information.
	 */
	public Product(ProductDto productDto) {
		this.productName = productDto.getProductName();
		this.price = productDto.getPrice();
	}
}

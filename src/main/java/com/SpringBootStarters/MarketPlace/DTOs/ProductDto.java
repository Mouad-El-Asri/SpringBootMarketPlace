package com.SpringBootStarters.MarketPlace.DTOs;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a data transfer object (DTO) for a product.
 * It contains the product name and price.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
	@NotBlank(message = "Product name must not be blank")
	private String productName;

	@Positive(message = "Price must be a positive number")
	private BigDecimal price;
}

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
	@NotNull(message = "Product id must not be null")
	@Min(value = 1, message = "Product id must be at least 1")
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
	 * Constructs a new Product object based on the provided ProductDto.
	 * 
	 * @param productDto The ProductDto object containing the Product information.
	 */
	public Product(ProductDto productDto) {
		this.productName = productDto.getProductName();
		this.price = productDto.getPrice();
	}
}

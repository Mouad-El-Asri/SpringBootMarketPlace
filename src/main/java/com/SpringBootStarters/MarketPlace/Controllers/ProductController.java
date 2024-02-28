package com.SpringBootStarters.MarketPlace.Controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootStarters.MarketPlace.DTOs.ProductDto;
import com.SpringBootStarters.MarketPlace.Entities.Product;
import com.SpringBootStarters.MarketPlace.Services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping(path = "api/v1/products")
@Tag(name = "Product", description = "The product API")
@Validated
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * Retrieve a list of all the products
	 * @return The list of products
	 */
	@GetMapping("/")
	@Operation(summary = "Get all products", description = "Get all products data from the database", responses = {
			@ApiResponse(responseCode = "200", description = "The list of products")
	})
	public ResponseEntity<List<Product>> getProducts() {
		logger.info("Get a list of all products");
		return ResponseEntity.ok(this.productService.getProducts());
	}

	/**
	 * Get product by id
	 * @param id The product id
	 * @return The product object
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Get a product by its id", description = "Get product data from the database", responses = {
			@ApiResponse(responseCode = "200", description = "The product object")
	})
	public ResponseEntity<Product> getProduct(@PathVariable("id") @Positive(message = "Id must be a positive number") long id) {
		logger.info("Get product by id");
		return ResponseEntity.ok(this.productService.getProduct(id));
	}

	/**
	 * Get a list of products for a product
	 * @param id The order id
	 * @return The list of products
	 */
	@GetMapping("/order/{id}")
	@Operation(summary = "Get a list of products for an order", description = "Get a list of products for an order", responses = {
			@ApiResponse(responseCode = "200", description = "The list of products")
	})
	public ResponseEntity<List<Product>> getProductsForOrder(@PathVariable("id") @Positive(message = "Id must be a positive number") long id) {
		logger.info("Get a list of orders for a product");
		return ResponseEntity.ok(this.productService.getProductsForOrder(id));
	}

	/**
	 * Create a new product
	 * @param productDto The Product DTO containg the new product information
	 * @return The new created product
	 */
	@PostMapping("/create")
	@Operation(summary = "Create a new product", description = "Create new product and add it to the database", responses = {
			@ApiResponse(responseCode = "201", description = "The new product")
	})
	public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDto productDto) {
		logger.info("Create a new product");
		return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.createProduct(productDto));
	}

	/**
	 * Updates a product by its id and saves it in the database
	 * @param id         The id of the product to be updated
	 * @param productDto The DTO containing product information
	 * @return The updated product
	 */
	@PutMapping("/update/{id}")
	@Operation(summary = "Update a product by its id", description = "Update  aproduct and save it in the database", responses = {
			@ApiResponse(responseCode = "200", description = "The updated product")
	})
	public ResponseEntity<Product> updateProduct(@PathVariable("id") @Positive(message = "Id must be a positive number") long id, @RequestBody @Valid ProductDto productDto) {
		logger.info("Update product");
		return ResponseEntity.ok(this.productService.updateProduct(id, productDto));
	}

	/**
	 * Deletes a product with the specified ID.
	 * @param id the ID of the product to delete
	 */
	@DeleteMapping("/delete/{id}")
	@Operation(summary = "Delete a product", description = "Delete a product from the database", responses = {
			@ApiResponse(responseCode = "200")
	})
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") @Positive(message = "Id must be a positive number") long id) {
		logger.info("Delete a product");
		this.productService.deleteProduct(id);
		return ResponseEntity.ok().build();
	}
}

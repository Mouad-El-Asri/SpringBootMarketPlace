package com.SpringBootStarters.FreshSpringBoot.Controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootStarters.FreshSpringBoot.Entities.Product;
import com.SpringBootStarters.FreshSpringBoot.Services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/products")
@Tag(name = "Product", description = "The product API")
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
	@Operation(
		summary = "Get all products",
		description = "Get all products data from the database",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "The list of products"
			)
		}
	)
	public List<Product> getProducts() {
		logger.info("Get a list of all products");
		return this.productService.getProducts();
	}


	/**
	 * Get product by id
	 * @param id The product id
	 * @return The product object
	 */
	@GetMapping("/{id}")
	@Operation(
		summary = "Get a product by its id",
		description = "Get product data from the database",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "The product object"
			)
		}
	)
	public Optional<Product> getProduct(@Valid @PathVariable long id) {
		logger.info("Get product by id");
		return this.productService.getProduct(id);
	}


	/**
	 * Create a new product
	 * @param product The product to be created
	 * @return The new created product
	 */
	@PostMapping("/create")
	@Operation(
		summary = "Create a new product",
		description = "Create new product and add it to the database",
		responses = {
			@ApiResponse(
				responseCode = "201",
				description = "The new product"
			)
		}
	)
	public Product createProduct(@NonNull @Valid @RequestBody Product product) {
		logger.info("Create a new product");
		return this.productService.createProduct(product);
	}

	/**
	 * Updates a product by its id and saves it in the database
	 * @param id      The id of the product to be updated
	 * @param product The updated product object
	 * @return The updated product
	 */
	@PutMapping("update/{id}")
	@Operation(
		summary = "Update a product by its id",
		description = "Update  aproduct and save it in the database",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "The updated product"
			)
		}
	)
	public Product updateProduct(@Valid @PathVariable long id, @Valid @RequestBody Product product) {
		logger.info("Update product");
		return this.productService.updateProduct(id, product);
	}


	/**
	 * Deletes a product with the specified ID.
	 * @param id the ID of the product to delete
	 */
	@DeleteMapping("/delete/{id}")
	@Operation(
		summary = "Delete a product",
		description = "Delete a product from the database",
		responses = {
			@ApiResponse(
				responseCode = "200"
			)
		}
	)
	public void deleteProduct(@Valid @PathVariable long id) {
		logger.info("Delete a product");
		this.productService.deleteProduct(id);
	}
}

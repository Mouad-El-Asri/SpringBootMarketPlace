package com.SpringBootStarters.FreshSpringBoot.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.SpringBootStarters.FreshSpringBoot.Entities.Product;
import com.SpringBootStarters.FreshSpringBoot.Repositories.ProductRepository;

import jakarta.validation.Valid;

import org.springframework.lang.NonNull;

@Service
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}


	/**
	 * Retrieve a list of all the products
	 * 
	 * @return List of products
	 */
	public List<Product> getProducts() {
		return this.productRepository.findAll();
	}


	/**
	 * Retrieves a product by its ID.
	 * 
	 * @param id the ID of the product to retrieve
	 * @return an Optional containing the product if found, or an empty Optional if
	 *         not found
	 */
	public Optional<Product> getProduct(long id) {
		return this.productRepository.findById(id);
	}


	/**
	 * Creates a new product and saves it to the database.
	 * 
	 * @param product the product to be created
	 * @return the created product
	 */
	public Product createProduct(@NonNull Product product) {
		return this.productRepository.save(product);
	}


	/**
	 * Updates a product with the given ID.
	 * @param id      the ID of the product to update
	 * @param product the updated product object
	 * @return the updated product
	 */
	public Product updateProduct(long id, Product product) {
		if (product == null)
			throw new IllegalArgumentException("Product can't be null");
		Product existingProduct = this.productRepository.findById(id).orElse(null);
		existingProduct.setProductName(product.getProductName());
		existingProduct.setPrice(product.getPrice());
		return this.productRepository.save(existingProduct);
	}


	/**
	 * Deletes a product with the specified ID.
	 * @param id the ID of the product to be deleted
	 */
	public void deleteProduct(long id) {
		this.productRepository.deleteById(id);
	}
}

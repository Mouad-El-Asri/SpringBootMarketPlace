package com.SpringBootStarters.MarketPlace.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.SpringBootStarters.MarketPlace.DTOs.ProductDto;
import com.SpringBootStarters.MarketPlace.Entities.Product;
import com.SpringBootStarters.MarketPlace.Repositories.ProductRepository;

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
	 * Retrieves a list of products for a given order.
	 * @param id The ID of the order
	 * @return List of products
	 */
	public List<Product> getProductsForOrder(long id) {
		return this.productRepository.findByOrdersId(id);
	}

	/**
	 * Creates a new product and saves it to the database.
	 * 
	 * @param product the product to be created
	 * @return the created product
	 */
	public Product createProduct(ProductDto productDto) {
		Optional<Product> productOptional = this.productRepository.findByProductName(productDto.getProductName());
		if (productOptional.isPresent())
			throw new IllegalStateException("Product already exists");
		Product product = new Product(productDto);
		return this.productRepository.save(product);
	}

	/**
	 * Updates a product with the given ID.
	 * 
	 * @param id         The ID of the product to update
	 * @param productDto The product DTO containing product information
	 * @return The updated product
	 * @throws IllegalStateException if the productDto is null
	 */
	public Product updateProduct(long id, ProductDto productDto) {
		if (productDto == null)
			throw new IllegalStateException("Product can't be null");
		Product existingProduct = this.productRepository.findById(id).orElse(null);
		existingProduct.setProductName(productDto.getProductName());
		existingProduct.setPrice(productDto.getPrice());
		return this.productRepository.save(existingProduct);
	}

	/**
	 * Deletes a product with the specified ID.
	 * 
	 * @param id the ID of the product to be deleted
	 */
	public void deleteProduct(long id) {
		this.productRepository.deleteById(id);
	}
}

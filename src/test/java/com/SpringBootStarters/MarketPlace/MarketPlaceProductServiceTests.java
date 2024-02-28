package com.SpringBootStarters.MarketPlace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.SpringBootStarters.MarketPlace.DTOs.ProductDto;
import com.SpringBootStarters.MarketPlace.Entities.Product;
import com.SpringBootStarters.MarketPlace.Repositories.ProductRepository;
import com.SpringBootStarters.MarketPlace.Services.ProductService;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class MarketPlaceProductServiceTests {
	private static final Logger logger = LoggerFactory.getLogger(MarketPlaceProductServiceTests.class);

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

	@BeforeAll
	public static void setUp() {
		logger.info("Starting product service tests");
	}

	@Test
	public void testGetProducts() {
		logger.info("Testing getProducts method");

		List<Product> products = Arrays.asList(
			new Product(1L, "Nike Air Jordan 1 Low", BigDecimal.valueOf(1500)),
			new Product(2L, "Nike Air Jordan 2 High", BigDecimal.valueOf(1700))
		);

		// Testing the retrieval of a list of products
		Mockito.when(productRepository.findAll()).thenReturn(products);
		List<Product> result = productService.getProducts();
		assertEquals(products, result);

		// Verify
		Mockito.verify(productRepository, Mockito.times(1)).findAll();
	}

	@Test
	public void testGetProduct() {
		logger.info("Testing getProduct method");

		Product product = new Product(1L, "Nike Air Jordan 2 High", BigDecimal.valueOf(1700));

		// Testing the retrieval of a product
		Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
		Product result = productService.getProduct(1L);
		assertEquals(product, result);

		// Testing the retrieval of a non-existing product
		Mockito.when(productRepository.findById(2L)).thenReturn(Optional.empty());
		EntityNotFoundException thrown = assertThrowsExactly(EntityNotFoundException.class, () -> {
			productService.getProduct(2L);
		});
		assertTrue(thrown.getMessage().contains("Product with id 2 doesn't exist"));

		// Verify
		Mockito.verify(productRepository, Mockito.times(1)).findById(1L);
	}

	@Test
	public void testGetProductsForOrder() {
		logger.info("Testing getProductForOrder method");

		List<Product> products = Arrays.asList(
			new Product(1L, "Nike Air Jordan 1 Low", BigDecimal.valueOf(1500)),
			new Product(2L, "Nike Air Jordan 2 High", BigDecimal.valueOf(1700))
		);

		// Testing the retrieval of a list of products for an order
		Mockito.when(productRepository.findByOrdersId(1L)).thenReturn(products);
		List<Product> result = productService.getProductsForOrder(1L);
		assertEquals(result, products);

		// Testing the retrieval of a list of products for a non-existing order
		Mockito.when(productRepository.findByOrdersId(2L)).thenThrow(new EntityNotFoundException("No products found for order with id 2"));
		EntityNotFoundException thrown = assertThrowsExactly(EntityNotFoundException.class, () -> productService.getProductsForOrder(2L));
		assertTrue(thrown.getMessage().contains("No products found for order with id 2"));

		// Verify
		Mockito.verify(productRepository, Mockito.times(2)).findByOrdersId(Mockito.anyLong());
	}

	@Test
	public void testCreateProduct() {
		logger.info("Testing createProduct method");

		ProductDto productDto = new ProductDto("Nike Air Jordan 1 Low", BigDecimal.valueOf(1500));
		Product product = new Product(1L, "Nike Air Jordan 1 Low", BigDecimal.valueOf(1500));

		// Testing the creation of a new product
		Mockito.when(productRepository.findByProductName("Nike Air Jordan 1 Low")).thenReturn(Optional.empty());
		Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
		Product result = productService.createProduct(productDto);
		assertEquals(product, result);

		// Testing the creation of a product with an existing name
		Mockito.when(productRepository.findByProductName("Nike Air Jordan 1 Low")).thenReturn(Optional.of(product));
		IllegalStateException thrown = assertThrowsExactly(IllegalStateException.class, () -> productService.createProduct(productDto));
		assertTrue(thrown.getMessage().contains("Product name already taken"));

		// Testing the creation of a product with a null productDto
		IllegalArgumentException thrown2 = assertThrowsExactly(IllegalArgumentException.class, () -> productService.createProduct(null));
		assertTrue(thrown2.getMessage().contains("Product can't be null"));

		Mockito.verify(productRepository, Mockito.times(2)).findByProductName(Mockito.anyString());
		Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
	}

	@Test
	public void testUpdateProduct() {
		logger.info("Testing updateProduct method");

		ProductDto productDto = new ProductDto("Nike Air Jordan 1 Low", BigDecimal.valueOf(1500));
		Product product = new Product(1L, "Nike Air Jordan 1 Low", BigDecimal.valueOf(1500));

		// Testing the update of a product
		Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
		Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
		Product result = productService.updateProduct(1L, productDto);
		assertEquals(product, result);

		// Testing the update of a non-existing product
		Mockito.when(productRepository.findById(2L)).thenReturn(Optional.empty());
		EntityNotFoundException thrown = assertThrowsExactly(EntityNotFoundException.class, () -> productService.updateProduct(2L, productDto));
		assertTrue(thrown.getMessage().contains("Product with id 2 doesn't exist"));

		// Testing the update of a product with a null productDto
		IllegalArgumentException thrown2 = assertThrowsExactly(IllegalArgumentException.class, () -> productService.updateProduct(1L, null));
		assertTrue(thrown2.getMessage().contains("Product can't be null"));

		// Verify
		Mockito.verify(productRepository, Mockito.times(2)).findById(Mockito.anyLong());
		Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
	}

	@Test
	public void testDeleteProduct() {
		logger.info("Testing deleteProduct method");

		// Testing the deletion of a product
		Mockito.when(productRepository.existsById(1L)).thenReturn(true);
		productRepository.deleteById(1L);

		// Testing the deletion of a non-existing product
		Mockito.when(productRepository.existsById(2L)).thenReturn(false);
		EntityNotFoundException thrown = assertThrowsExactly(EntityNotFoundException.class, () -> productService.deleteProduct(2L));
		assertTrue(thrown.getMessage().contains("Product with id 2 doesn't exist"));

		// Verify
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
	}

	@AfterAll
	public static void tearDown() {
		logger.info("Finishing product service tests");
	}
}

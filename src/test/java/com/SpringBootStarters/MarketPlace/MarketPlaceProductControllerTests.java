package com.SpringBootStarters.MarketPlace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.SpringBootStarters.MarketPlace.Controllers.ProductController;
import com.SpringBootStarters.MarketPlace.DTOs.ProductDto;
import com.SpringBootStarters.MarketPlace.Entities.Product;
import com.SpringBootStarters.MarketPlace.Services.ProductService;

@SpringBootTest
public class MarketPlaceProductControllerTests {
	private static final Logger logger = LoggerFactory.getLogger(MarketPlaceProductControllerTests.class);

	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductController productController;

	@BeforeAll
	public static void setUp() {
		logger.info("Starting product controller tests");
	}

	@Test
	public void testGetProducts() {
		logger.info("Testing getProducts method");

		List<Product> products = Arrays.asList(
			new Product(1L, "Nike air jordan 1 low", BigDecimal.valueOf(1500)),
			new Product(2L, "Nike air jordan 2 high", BigDecimal.valueOf(1700))
		);

		// Testing the retrieval of a list of products
		Mockito.when(productService.getProducts()).thenReturn(products);
		ResponseEntity<List<Product>> responseEntity = productController.getProducts();
		assertFalse(products.isEmpty());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(products, responseEntity.getBody());

		// Verify
		Mockito.verify(productService, Mockito.times(1)).getProducts();
	}
	
	@Test
	public void testGetProduct() {
		logger.info("Testing getProduct method");

		Product product = new Product(1L, "Nike air jordan low", BigDecimal.valueOf(1500));

		// Testing the retrieval of a product
		Mockito.when(productService.getProduct(1L)).thenReturn(product);
		ResponseEntity<Product> responseEntity = productController.getProduct(1L);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(product, responseEntity.getBody());

		// Verify
		Mockito.verify(productService, Mockito.times(1)).getProduct(1L);
	}

	@Test
	public void testGetProductsForOrder() {
		logger.info("Testing getProductForOrder method");

		List<Product> products = Arrays.asList(
			new Product(1L, "Nike air jordan 1 low", BigDecimal.valueOf(1500)),
			new Product(2L, "Nike air jordan 2 high", BigDecimal.valueOf(1700))
		);

		// Testing the retrieval of a list of products for an order
		Mockito.when(productService.getProductsForOrder(1L)).thenReturn(products);
		ResponseEntity<List<Product>> responseEntity = productController.getProductsForOrder(1L);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(products, responseEntity.getBody());

		// Verify
		Mockito.verify(productService, Mockito.times(1)).getProductsForOrder(1L);
	}

	@Test
	public void testCreateProduct() {
		logger.info("Testing createProduct method");

		ProductDto productDto = new ProductDto("Nike air jordan low", BigDecimal.valueOf(1500));
		Product product = new Product(1L, "Nike air jordan low", BigDecimal.valueOf(1500));

		// Testing the creation of a new product
		Mockito.when(productService.createProduct(productDto)).thenReturn(product);
		ResponseEntity<Product> responseEntity = productController.createProduct(productDto);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(product, responseEntity.getBody());

		// Verify
		Mockito.verify(productService, Mockito.times(1)).createProduct(productDto);
	}

	@Test
	public void testUpdateProduct() {
		logger.info("Testing updateProduct method");

		ProductDto productDto = new ProductDto("Nike air jordan low", BigDecimal.valueOf(1500));
		Product product = new Product(1L, "Nike air jordan low", BigDecimal.valueOf(1500));

		// Testing the update of a product
		Mockito.when(productService.updateProduct(1L, productDto)).thenReturn(product);
		ResponseEntity<Product> responseEntity = productController.updateProduct(1L, productDto);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(product, responseEntity.getBody());

		// Verify
		Mockito.verify(productService, Mockito.times(1)).updateProduct(1L, productDto);
	}

	@Test
	public void testDeleteProduct() {
		logger.info("Testing deleteProduct method");

		// Testing the deletion of a product
		Mockito.doNothing().when(productService).deleteProduct(1L);
		ResponseEntity<Void> responseEntity = productController.deleteProduct(1L);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(null, responseEntity.getBody());

		// Verify
		Mockito.verify(productService, Mockito.times(1)).deleteProduct(1L);;
	}
	
	@AfterAll
	public static void tearDown() {
		logger.info("Finishing product controller tests");
	}
}

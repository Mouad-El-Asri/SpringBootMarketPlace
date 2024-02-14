package com.SpringBootStarters.MarketPlace.DTOs;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the data transfer object (DTO) for an order.
 * It contains the total amount of the order.
 */
@Setter
@Getter
public class OrderDto {
	@NotNull(message = "Order total amount must not be null")
	private BigDecimal totalAmount;
}

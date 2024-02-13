package com.SpringBootStarters.MarketPlace.DTOs;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDto {
	@NotNull(message = "Order total amount must not be null")
	private BigDecimal totalAmount;
}

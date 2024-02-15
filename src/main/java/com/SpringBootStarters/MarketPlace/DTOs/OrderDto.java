package com.SpringBootStarters.MarketPlace.DTOs;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
	private List<Long> productIds;
}

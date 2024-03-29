package com.SpringBootStarters.MarketPlace.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a Data Transfer Object (DTO) for a customer.
 * It contains the customer's information such as first name, last name, email,
 * and age.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
	@NotBlank(message = "Customer firstname must not be blank")
	@Size(min = 1, max = 100, message = "Customer firstname must be between 1 and 100 characters")
	private String firstName;

	@NotBlank(message = "Customer lastname must not be blank")
	@Size(min = 1, max = 100, message = "Customer lastname must be between 1 and 100 characters")
	private String lastName;

	@Email(message = "Customer email must be a valid email address")
	private String email;

	@Positive(message = "Customer age must be a positive number")
	private int age;
}

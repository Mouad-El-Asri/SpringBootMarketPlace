package com.SpringBootStarters.MarketPlace.Entities;

import com.SpringBootStarters.MarketPlace.DTOs.CustomerDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a customer entity with their information.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	@NotBlank(message = "Customer firstname must not be blank")
	@Size(min = 1, max = 100, message = "Customer firstname must be between 1 and 100 characters")
	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;

	@NotBlank(message = "Customer lastname must not be blank")
	@Size(min = 1, max = 100, message = "Customer lastname must be between 1 and 100 characters")
	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;

	@Email(message = "Customer email must be a valid email address")
	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@Positive(message = "Customer age must be a positive number")
	@Column(name = "age", nullable = false)
	private int age;

	/**
	 * Constructs a new Customer object based on the provided CustomerDto.
	 * @param customerDto The CustomerDto object containing the customer information.
	 */
	public Customer(CustomerDto customerDto) {
		this.firstName = customerDto.getFirstName();
		this.lastName = customerDto.getLastName();
		this.email = customerDto.getEmail();
		this.age = customerDto.getAge();
	}
}

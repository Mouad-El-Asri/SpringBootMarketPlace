package com.SpringBootStarters.FreshSpringBoot.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a customer entity with their information.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotBlank(message = "Customer id must not be blank")
	@Min(value = 1, message = "Customer id must be at least 1")
	private long id;

	@NotBlank(message = "Customer firstname must not be blank")
	private String firstName;

	@NotBlank(message = "Customer lastname must not be blank")
	private String lastName;

	@Email(message = "Customer email must be a valid email address")
	private String email;

	@Positive(message = "Customer age must be a positive number")
	private int age;

	/**
	 * Constructs a new Customer object with the specified properties.
	 * @param firstName the customer's first name
	 * @param lastName  the customer's last name
	 * @param email     the customer's email address
	 * @param age       the customer's age
	 */
	public Customer(long id, String firstName, String lastName, String email, int age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.age = age;
	}
}

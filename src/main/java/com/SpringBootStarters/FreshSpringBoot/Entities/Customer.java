package com.SpringBootStarters.FreshSpringBoot.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a customer entity with their information.
 */
@Getter
@Setter
@Entity
@Table(name = "Customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private int age;

	public Customer() {
	}

	/**
	 * Constructs a new Customer object with the specified properties.
	 *
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

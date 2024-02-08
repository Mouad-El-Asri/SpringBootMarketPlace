package com.SpringBootStarters.FreshSpringBoot.Entities;

/**
 * Represents a customer with their information.
 */
public class Customer {
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private int age;

	/**
	 * Constructs a new Customer object with the specified properties.
	 *
	 * @param id        the customer ID
	 * @param firstName the customer's first name
	 * @param lastName  the customer's last name
	 * @param email     the customer's email address
	 * @param age       the customer's age
	 */
	public Customer(long id, String firstName, String lastName, String email, int age) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.age = age;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}

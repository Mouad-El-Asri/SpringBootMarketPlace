package com.SpringBootStarters.MarketPlace.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBootStarters.MarketPlace.Entities.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
	List<Orders> findByCustomerId(long customerId);
	List<Orders> findByProductsId(long productId);
}

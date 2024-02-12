package com.SpringBootStarters.FreshSpringBoot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBootStarters.FreshSpringBoot.Entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}

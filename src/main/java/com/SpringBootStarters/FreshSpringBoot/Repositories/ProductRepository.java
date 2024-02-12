package com.SpringBootStarters.FreshSpringBoot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBootStarters.FreshSpringBoot.Entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

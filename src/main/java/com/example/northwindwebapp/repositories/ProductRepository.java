package com.example.northwindwebapp.repositories;

import com.example.northwindwebapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
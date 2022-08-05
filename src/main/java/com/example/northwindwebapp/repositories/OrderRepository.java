package com.example.northwindwebapp.repositories;

import com.example.northwindwebapp.entities.Customer;
import com.example.northwindwebapp.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	@Transactional
	List<Order> findAllByCustomerID(Customer customerId);
}
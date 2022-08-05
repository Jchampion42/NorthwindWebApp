package com.example.northwindwebapp.repositories;

import com.example.northwindwebapp.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}
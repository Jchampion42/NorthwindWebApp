package com.example.northwindwebapp.repositories;

import com.example.northwindwebapp.entities.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShipperRepository extends JpaRepository<Shipper, Integer> {
}
package com.example.northwindwebapp.repositories;

import com.example.northwindwebapp.entities.Order;
import com.example.northwindwebapp.entities.OrderDetail;
import com.example.northwindwebapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    OrderDetail findOrderDetailById_OrderIDAndId_ProductID(int orderID, int productID);

    @Transactional
    List<OrderDetail> findAllByProductID(Product productId);
    @Transactional
    void deleteAllByOrderID(Order order);
}
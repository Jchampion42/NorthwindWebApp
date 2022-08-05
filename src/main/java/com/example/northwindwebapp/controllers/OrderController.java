package com.example.northwindwebapp.controllers;

import com.example.northwindwebapp.entities.Order;
import com.example.northwindwebapp.repositories.OrderDetailRepository;
import com.example.northwindwebapp.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class OrderController {

    @Autowired
    OrderRepository repo;
    @Autowired
    OrderDetailRepository orderDetailRepo;

    @GetMapping("/orders/{id}")
    public String getOrder(@PathVariable int id, Model model){
        Optional<Order> result = repo.findById(id);
        List<String> orderData;
        if (result.isPresent()){
            orderData = generateOrderList(result.get());
        }
        else{
            model.addAttribute("message", "Order not found");
            return "missing";
        }

        List<List<String>> orders = new ArrayList();
        orders.add(orderData);

        model.addAttribute("title", "Order from northwind");

        sendToData(model, orders);
        return "data";

    }

    @GetMapping("/orders")
    public String getAllOrders(Model model){
        List<Order> result = repo.findAll();
        List orders = new ArrayList();

        for (Order order : result){
            orders.add(generateOrderList(order));
        }

        sendToData(model, orders);
        model.addAttribute("title", "All orders from northwind");
        model.addAttribute("source", "orders");
        return "data";

    }

    @GetMapping("/orders/{id}/edit")
    public String editOrder(@PathVariable int id, Model model){
        Optional<Order> result = repo.findById(id);
        if (result.isPresent()){
            Order order = result.get();
            model.addAttribute("orderToEdit", order);
            return "order/orderEditForm";
        }
        else{
            model.addAttribute("message", "Order not found");
            return "missing";
        }
    }

    @PostMapping("/orders/{id}/edit")
    public String updateOrder(@ModelAttribute("orderToEdit") Order order, Model model){
        repo.save(order);
        model.addAttribute("source", "orders");
        model.addAttribute("message", "Order updated successfully!");
        return "updateSuccess";
    }

    @GetMapping("/orders/create")
    public String createOrder(Model model){
        Order newOrder = new Order();
        model.addAttribute("newOrder", newOrder);
        return "order/orderAddForm";
    }

    @PostMapping("/orders/create")
    public String addOrder(@ModelAttribute("newOrder") Order order, Model model){
        repo.save(order);
        model.addAttribute("message", "Successful creation");
        return "operationSuccess";
    }

    @PostMapping("/orders/{id}/delete")
    public String deleteOrder(@PathVariable int id, Model model){
        Optional<Order> result = repo.findById(id);
        if (result.isPresent()){
            Order order = result.get();

            orderDetailRepo.deleteAllByOrderID(order);
            repo.deleteById(id);
            model.addAttribute("source", "orders");
            model.addAttribute("message", "Order deleted successfully!");
            return "updateSuccess";
        }
        else{
            model.addAttribute("message", "Order not found");
            return "missing";
        }
    }

    public List<String> generateOrderList(Order order) {
        List<String> orderData = new ArrayList<>();
        orderData.add(String.valueOf(order.getId()));
        if (order.getCustomerID() == null){
            orderData.add(null);
        }else {
            orderData.add(order.getCustomerID().getId());

        }
        orderData.add(String.valueOf(order.getEmployeeID().getId()));
        orderData.add(String.valueOf(order.getOrderDate()));
        orderData.add(String.valueOf(order.getShippedDate()));
        orderData.add(String.valueOf(order.getRequiredDate()));
        orderData.add(String.valueOf(order.getShipVia().getId()));
        orderData.add(String.valueOf(order.getFreight()));
        orderData.add(order.getShipName());
        orderData.add(order.getShipAddress());
        orderData.add(order.getShipCity());
        orderData.add(order.getShipRegion());
        orderData.add(order.getShipPostalCode());
        orderData.add(order.getShipCountry());
        return orderData;
    }

    public void sendToData(Model model, List orders){
        String[] fieldNames = {"Order ID", "Customer ID", "Employee ID", "Order Date", "Shipped Date", "Required Date", "Ship Via", "Freight", "Ship Name", "Ship Address", "Ship City", "Ship Region", "Ship Postal Code", "Ship Country"};
        model.addAttribute("fieldNames", fieldNames);
        model.addAttribute("entityList", orders);
        model.addAttribute("source", "orders");

    }

}

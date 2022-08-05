package com.example.northwindwebapp.controllers;

import com.example.northwindwebapp.entities.Order;
import com.example.northwindwebapp.entities.Shipper;
import com.example.northwindwebapp.repositories.OrderDetailRepository;
import com.example.northwindwebapp.repositories.OrderRepository;
import com.example.northwindwebapp.repositories.ShipperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class ShipperController {
    private static final String[] SHIPPERS_FIELD_NAMES = {"id", "Company Name", "Phone Number"};
    @Autowired
    ShipperRepository repository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @GetMapping("/shippers")
    public String getAllShippers(Model model){
        List<Shipper> result = repository.findAll();
        List shippers = new ArrayList();

        for (Shipper shipper : result){
            shippers.add(ShippersListGenerator.shippersDataGenerator(shipper));
        }
        model.addAttribute("title", "Shippers from northwind");
        sendToData(model, shippers);
        model.addAttribute("title", "Shippers");
        return "data";
    }


    @GetMapping("/shippers/{id}")
    public String getShippersByID(@PathVariable int id, Model model) {
        Optional<Shipper> result = repository.findById(id);
        List<String> shippersData;
        if (result.isPresent()){
            shippersData = ShippersListGenerator.shippersDataGenerator(result.get());
        } else{
            model.addAttribute("message", "Product not found");
            return "missing";
        }
        List<List<String>> shippersList = new ArrayList();
        shippersList.add(shippersData);

        String[] fieldNames = {"Shipper ID", "Company Name", "Phone Number"};
        model.addAttribute("title", "Shippers");
        model.addAttribute("fieldNames", fieldNames);
        model.addAttribute("entityList", shippersList);
        return "data";

    }
    @GetMapping("/shippers/create")
    public String displayCreateShipper(Model model) {
        model.addAttribute("shipper", new Shipper());
        return "shipper/createShipper";
    }

    @PostMapping("/shippers/create")
    public String createShipper(@ModelAttribute("shipper") Shipper shipper) {
        repository.save(shipper);
        return "data";
    }


    @GetMapping("/shippers/{id}/edit")
    public String displayShippersEditForm(@PathVariable int id, Model model){
        Shipper shipper = repository.findById(id).get();
        model.addAttribute("shipperToUpdate", shipper);
        return "shipper/updateShipperForm";

    }

    @PostMapping("/shippers/{id}/edit")
    public String updateShipper(@ModelAttribute("shipperToUpdate") Shipper shipper, Model model){
        repository.save(shipper);
        model.addAttribute("message", "Shipper Successfully Updated");
        return "operationSuccess";
    }

    @PostMapping("/shippers/{id}/delete")
    public String deleteShipper(@PathVariable int id, Model model){
        Optional<Shipper> result = repository.findById(id);
        if(result.isPresent()){
            Shipper shipper = result.get();

            Set<Order> orderSet = shipper.getOrders();
            for (Order order: orderSet) {
                orderDetailRepository.deleteAllByOrderID(order);
            }
            orderRepository.deleteAll(orderSet);
            repository.delete(shipper);

            model.addAttribute("message", "Shipper Deleted Successfully");
            model.addAttribute("source", "shippers");
            return "deleteSuccess";
        }
        else{
            model.addAttribute("message", "Shipper Not Found");
            return "missing";
        }
    }


    private static class ShippersListGenerator {
        private static List<String> shippersDataGenerator(Shipper shipper) {
            List<String> shipperData = new ArrayList<>();
            shipperData.add(String.valueOf(shipper.getId()));
            shipperData.add(shipper.getCompanyName());
            shipperData.add(shipper.getPhone());
            return shipperData;
        }
    }

    public void sendToData(Model model, List shippers){
        model.addAttribute("fieldNames", SHIPPERS_FIELD_NAMES);
        model.addAttribute("entityList", shippers);
        model.addAttribute("source", "shippers");
        model.addAttribute("name", "shippers");
    }
}

package com.example.northwindwebapp.controllers;

import com.example.northwindwebapp.entities.OrderDetail;
import com.example.northwindwebapp.repositories.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderDetailController {
    @Autowired
    OrderDetailRepository repo;

    @GetMapping("orderDetails/{oId}/{pId}")
    public String getById(@PathVariable int oId, @PathVariable int pId, Model model){
        Optional<OrderDetail> result = Optional.ofNullable(repo.findOrderDetailById_OrderIDAndId_ProductID(oId, pId));
        List<String> odData = new ArrayList<>();
        OrderDetail od;
        if (result.isPresent()){
            od = result.get();
            odData.add(String.valueOf(od.getId().getOrderID()));
            odData.add(String.valueOf(od.getId().getProductID()));
            odData.add(String.valueOf(od.getUnitPrice()));
            odData.add(String.valueOf(od.getQuantity()));
            odData.add(String.valueOf(od.getDiscount()));
        } else {
            model.addAttribute("message", "Order Details not found");
            return "missing";
        }
        List<List<String>> dataList = new ArrayList<>();
        dataList.add(odData);

        String[] fieldNames = {"Order ID","Product ID","Unit Price","Quantity", "Discount"};
        model.addAttribute("title", "Order Details from northwind");
        model.addAttribute("fieldNames", fieldNames);
        model.addAttribute("entityList", dataList);

        return "data";
    }

    @GetMapping("orderDetails")
    public String getAllOds(Model model){   //need to test if this works
        List<OrderDetail> result = repo.findAll();
        List ods = new ArrayList();

        for (OrderDetail product : result){
            ods.add(generateProductList(product));
        }
        model.addAttribute("title", "Order details from northwind");
        sendToData(model, ods);
        model.addAttribute("title", "All details from northwind");

        return "data";
    }

    public List generateProductList(OrderDetail od){
        List<String> odl = new ArrayList<>();

        odl.add(String.valueOf(od.getId().getOrderID()));
        odl.add(String.valueOf(od.getId().getProductID()));
        odl.add(String.valueOf(od.getUnitPrice()));
        odl.add(String.valueOf(od.getQuantity()));
        odl.add(String.valueOf(od.getDiscount()));

        return odl;
    }

    public void sendToData(Model model, List ods){
        String[] fieldNames = {"Order ID","Product ID","Unit Price","Quantity", "Discount"};
        model.addAttribute("fieldNames", fieldNames);
        model.addAttribute("entityList", ods);
        model.addAttribute("source","orderDetails");
        model.addAttribute("idNum",2);
    }

    @GetMapping("/orderDetails/{oId}/{pId}/edit")
    public String editOd(@PathVariable int oId, @PathVariable int pId, Model model){

        Optional<OrderDetail> result = Optional.ofNullable(repo.findOrderDetailById_OrderIDAndId_ProductID(oId, pId));
        OrderDetail od = null;
        if (result.isPresent()){
            od = result.get();
            System.out.println("" + od.getId() +  od.getId().getProductID().toString());
            model.addAttribute("oId",od.getId().getOrderID());
            model.addAttribute("pId",od.getId().getProductID());
            model.addAttribute("orderDetail", od);
            return "orderDetail/updateorderdetail";

        } else {
            model.addAttribute("message", "Order Details not found");
            return "missing";
        }
    }

    @PostMapping("/orderDetails/{oId}/{pId}/edit")
    public String updateOd(@ModelAttribute("odToEdit") OrderDetail od, Model model){
        Instant.now();
        repo.save(od);
//        repo.saveAndFlush(od);
        model.addAttribute("message", "Order details updated.");
        model.addAttribute("source", "orderDetails");
        return "updateSuccess"; //TODO switch this out with any 'success' page we make
    }

    @PostMapping("/orderDetails/{oId}/{pId}/delete")
    public String deleteOd(@PathVariable int oId, @PathVariable int pId, Model model){
        Optional<OrderDetail> result = Optional.ofNullable(repo.findOrderDetailById_OrderIDAndId_ProductID(oId, pId));
        if(result.isPresent()){
            repo.delete(result.get());
            model.addAttribute("message", "Order details deleted.");
            model.addAttribute("source", "orderDetails");
            return "deleteSuccess";
        } else{
            return "missing";
        }

        //OrderDetail result = repo.findOrderDetailById_OrderIDAndId_ProductID(oId, pId);

    }

    //redirecting because create function is defined in ShopController
    @GetMapping("/orderDetails/create")
    public String directToShop(Model model){

        return "security/accessDenied";
    }


}

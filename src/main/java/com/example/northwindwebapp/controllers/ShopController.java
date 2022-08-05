package com.example.northwindwebapp.controllers;

import com.example.northwindwebapp.entities.Order;
import com.example.northwindwebapp.entities.OrderDetail;
import com.example.northwindwebapp.entities.OrderDetailId;
import com.example.northwindwebapp.entities.Product;
import com.example.northwindwebapp.repositories.OrderDetailRepository;
import com.example.northwindwebapp.repositories.OrderRepository;
import com.example.northwindwebapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@SessionAttributes("basket")
public class ShopController {

    @Autowired
    ProductRepository productRepo;

    @Autowired
    OrderDetailRepository orderDetailRepo;

    @Autowired
    OrderRepository orderRepo;

//    @ModelAttribute("basket")
//    public List<Product> basket(){
//        return new ArrayList<>();
//    }

    @ModelAttribute("basket")
    public Map basket(){ return new HashMap<Product, Integer>();}



    @GetMapping("/shop")
    public String displayShop(Model model, @ModelAttribute("basket") Map<Product, Integer> basket){
        List<Product> products = productRepo.findAll();
        model.addAttribute("allProducts", products);
        shopDisplayModelFormat(model, basket);
        return "shop/shop";
    }
    @GetMapping("/shop/basket")
    public String basketContents(Model model, @ModelAttribute ("basket") Map<Product,Integer> basket ){
//        List<Product> products = new ArrayList<>();
//        List<Integer> quantity = new ArrayList<>();
//        for(Product p : basket.keySet()){
//            products.add(p);
//            quantity.add(basket.get(p));
//        }
//        model.addAttribute(products);
//        model.addAttribute(quantity);
        shopDisplayModelFormat(model, basket);
        return "shop/basketDisplay";
    }
    @GetMapping("/shop/add/{id}")
    public String addProduct(@PathVariable int id, Model model,@ModelAttribute("basket") HashMap<Product,Integer> basket){
        Product product = productRepo.findById(id).get();
        shopDisplayModelFormat(model, basket);
        for (Product key: basket.keySet()){
            if (product.getId().equals(key.getId())){
                basket.replace(key, basket.get(key) + 1);
                return displayShop(model , basket);
            }
        }
        basket.put(product, 1);

//        for (int i=0;i<basket.size();i++){
//            if (basket.get(i).getId().equals(product.getId())){

//                temp = quantity.get(i);
//                quantity.set(i, temp+1);
//                return displayShop(model);
//            }
//        }
//        basket.add(product);
//        quantity.add(1);


        return displayShop(model , basket);
    }

    //maybe make a new int to track this
    //int sessionOrderId = getNewOrderId();
    /*public int getNewOrderId(){
        //get last orderDetail orderId + 1 for consistency and avoiding duplicates
        List<OrderDetail> resultsAll = orderDetailRepo.findAll();
        OrderDetail lastResult = resultsAll.get(resultsAll.size());
        return lastResult.getOrderID().getId() + 1;
    }*/

    @PostMapping("/shop/order-detail/create")
    public String createAllOrderDetails(@ModelAttribute("basket") Map<Product,Integer> basket, Model model){
        List<Order> resultsAll = orderRepo.findAll();
        Order lastResult = resultsAll.get(resultsAll.size()-1);
        int sessionOrderId = lastResult.getId() + 1;
        /*OrderDetailId odId = new OrderDetailId();
        odId.setOrderID(sessionOrderId);*/
        Order order = new Order();
        order.setId(sessionOrderId);
        order =orderRepo.save(order);
        List<Product> prodList = new ArrayList<>(basket.keySet());

//        List<Product> prodList=new ArrayList<>();
//        for (Product p : basket.keySet()){
//            prodList.add(p);
//        }
        orderRepo.save(order);


        for (Product product : prodList) {
            OrderDetail newOrderDetail = new OrderDetail();
            OrderDetailId id = new OrderDetailId();

            id.setOrderID(order.getId());
            id.setProductID(product.getId());

            newOrderDetail.setOrderID(order);
            newOrderDetail.setProductID(product);

            newOrderDetail.setId(id);
            newOrderDetail.setUnitPrice(product.getUnitPrice());
            //short sh = 1;
            //basket.get(product).shortValue()
            newOrderDetail.setQuantity(basket.get(product).shortValue());
            newOrderDetail.setDiscount(0.0d); //doing this since all existing orderDetails discount is 0, will change and apply it in 'shop' if needed

            orderDetailRepo.save(newOrderDetail);
        }

        basket.clear();

        model.addAttribute("message", "Successfully checked out, thank you for shopping with Northwind.");
        return "operationSuccess";

    }
    /*@GetMapping("/shop/order-detail/create")
    public String createOrderDetail(@ModelAttribute("basket") Map<Product,Integer> basket, Model model){
        OrderDetail newOrderDetail = new OrderDetail();

        Set<Product> prodList = basket.keySet();
        Product tempProduct = prodList.iterator().next();

        //newOrderDetail.setOrderID(sessionOrderId); //
        newOrderDetail.setProductID(tempProduct);
        newOrderDetail.setUnitPrice(tempProduct.getUnitPrice());
        newOrderDetail.setQuantity(basket.get(tempProduct).shortValue());
        newOrderDetail.setDiscount(0.0d); //doing this since all existing orderDetails discount is 0, will change and apply it in 'shop' if needed

        model.addAttribute("newOrderDetail", newOrderDetail);
        return "";
    }

    @PostMapping("/shop/order-detail/create")
    public String addOd(@ModelAttribute("newOrderDetail") OrderDetail orderDetail, Model model){
        orderDetailRepo.save(orderDetail);
        model.addAttribute("message", "Successful creation");
        return "operationSuccess";
    }*/

    @GetMapping("/shop/delete/{id}")
    public String deleteOrder(@PathVariable int id, @ModelAttribute("basket") Map<Product, Integer> basket, Model model ) {
//        boolean remove = true;
        Product product = productRepo.getReferenceById(id);

        for (Product p : basket.keySet()){
            if (p.getId() == id){
                if (basket.get(p)>1){
                    basket.put(p, basket.get(p)-1);
                    shopDisplayModelFormat(model, basket);
                    return "shop/basketDisplay";
                } else{
                    basket.remove(p);
                    shopDisplayModelFormat(model, basket);
                    return "shop/basketDisplay";
                }
            }
        }
        shopDisplayModelFormat(model, basket);
        return "shop/basketDisplay";

//        if (basket.get(product)>1){
//            basket.put(product, basket.get(product)-1);
//        } else{
//            basket.remove(product);
//        }
//        shopDisplayModelFormat(model, basket);


    }

    private void shopDisplayModelFormat(Model model, Map<Product, Integer> basket ){
        List<Product> products = new ArrayList<>();
        List<Integer> quantity = new ArrayList<>();
        for(Product p : basket.keySet()){
            products.add(p);
            quantity.add(basket.get(p));
        }
        model.addAttribute("products", products);
        model.addAttribute("quantity", quantity);
    }

}

package com.example.northwindwebapp.controllers;


import com.example.northwindwebapp.entities.OrderDetail;
import com.example.northwindwebapp.entities.Product;
import com.example.northwindwebapp.entities.Shipper;
import com.example.northwindwebapp.repositories.OrderDetailRepository;
import com.example.northwindwebapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    public static final String[] FIELD_NAMES = {"id", "Name", "Quanity per unit", "Reorder level", "Units in stock", "Unit price", "Units on order"};

    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @GetMapping("/")
    public String getIndex(){
        return"../static/Index.html";
    }
    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable int id, Model model ){
        Optional<Product> result = productRepository.findById(id);
        List<String> productData;
        if (result.isPresent()){
            productData = generateProductList(result.get());
        } else{
            model.addAttribute("message", "Product not found");
            return "missing";
        }
//        Actor result = repo.findById(id).get();
        List<List<String>> products = new ArrayList();
        products.add(productData);

        model.addAttribute("title", "Product from northwind");

        sendToData(model, products);
        return "data";
    }


    public List generateProductList(Product p){
        List<String> product = new ArrayList<>();
        product.add(String.valueOf(p.getId()));
        product.add(p.getProductName());
        product.add(String.valueOf(p.getReorderLevel()));
        product.add(String.valueOf(p.getUnitsInStock()));
        product.add(String.valueOf(p.getQuantityPerUnit()));
        product.add(String.valueOf(p.getUnitPrice()));
        product.add(String.valueOf(p.getUnitsOnOrder()));
        return product;
    }
//    public Product listToProduct(List<String> l){
//        Product p = repo.getReferenceById(Integer.parseInt(l.get(0)));
//        p.setProductName(l.get(1));
//        p.setReorderLevel(Integer.parseInt(l.get(2)));
//        p.setUnitsInStock(Integer.parseInt(l.get(3)));
//        p.setQuantityPerUnit((l.get(4)));
//        p.setUnitPrice(BigDecimal.valueOf(Integer.parseInt(l.get(5))));
//        p.setUnitsOnOrder(Integer.parseInt(l.get(6)));
//        return p;
//    }
    @GetMapping("/products/create")
    public String createProduct(Model model){
        model.addAttribute("newProduct", new Product());
        return "/product/createProduct";
    }
    @PostMapping("/products/create")
    public String createShipper(@ModelAttribute("newProduct") Product product) {
        productRepository.save(product);
        return "data";
    }

    @GetMapping("/products")
    public String getAllProducts(Model model){
        List<Product> result = productRepository.findAll();
        List products = new ArrayList();

        for (Product product : result){
            products.add(generateProductList(product));
        }
        model.addAttribute("title", "Products from northwind");
        sendToData(model, products);
        model.addAttribute("title", "All products from northwind");
//        model.addAttribute("idNum", 2);
        return "data";
    }
    public void sendToData(Model model, List products){
        model.addAttribute("fieldNames", FIELD_NAMES);
        model.addAttribute("entityList", products);
        model.addAttribute("source", "products");
        model.addAttribute("name", "products");
    }
    @GetMapping("/products/{id}/edit")
    public String editRow(Model model, @PathVariable int id){
        Optional<Product> result = productRepository.findById(id);
//        List<ReflectionUtils.MethodCallback> methodList = new ArrayList();
//        methodList.add(resu.getId);

        if (result.isPresent()){
            model.addAttribute("product", result.get());
//            model.addAttribute("source", "product");
//            model.addAttribute("entity", result);
//            model.addAttribute("record", generateProductList(result.get()));
//            model.addAttribute("fieldNames", FIELD_NAMES);
//            model.addAttribute("idNum", 1);
            return "product/productEdit";
        } else{
            return "missing";
        }
    }

    public void analyze(Object obj){
        ReflectionUtils.doWithFields(obj.getClass(), field -> field.setAccessible(true));
    }

public Product mergeAndSave(Product product){
        Product oldProduct = productRepository.getReferenceById(product.getId());
        oldProduct.setProductName(product.getProductName());
        oldProduct.setReorderLevel(product.getReorderLevel());
        oldProduct.setUnitsInStock(product.getUnitsInStock());
        oldProduct.setQuantityPerUnit(product.getQuantityPerUnit());
        oldProduct.setUnitPrice(product.getUnitPrice());
        oldProduct.setUnitsOnOrder(product.getUnitsOnOrder());
        productRepository.save(oldProduct);
        return oldProduct;
}

    @PostMapping("/products/update")
    public String updateRow(@ModelAttribute("product") Product product, Model model){
        mergeAndSave(product);
        model.addAttribute("source", "products");
        model.addAttribute("message", "Product updated successfully!");
        return "updateSuccess";
    }


    @PostMapping("/products/{id}/delete")
    public String deleteRow(@PathVariable int id, Model model) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product foundProduct = optionalProduct.get();
            List<OrderDetail> orderDetails = orderDetailRepository.findAllByProductID(foundProduct);
            for (OrderDetail orderDetail : orderDetails) {
                orderDetail.setProductID(null);
            }
            orderDetailRepository.deleteAllInBatch(orderDetails);
            productRepository.deleteById(foundProduct.getId());
            model.addAttribute("source", "products");
            return "deleteSuccess";
        } else return "missing";
    }
}

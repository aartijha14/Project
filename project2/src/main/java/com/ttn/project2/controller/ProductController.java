package com.ttn.project2.controller;

import com.ttn.project2.ExceptionHandler.ResourceNotFoundException;
import com.ttn.project2.Model.Product;
import com.ttn.project2.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public class ProductController {

    @Autowired
    ProductService productService;

    //API to Add Product
    @PostMapping("/add/product")
    public String addProduct(Product product){
        return productService.addProduct(product);

    }

    //API to Get Product Details
    @GetMapping("/view/product/{id}")
    public List<Product> viewProduct(@PathVariable long id)throws ResourceNotFoundException {
        return productService.viewProduct(id);
    }

    //API to delete Product
    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }
}
package com.ttn.project2.Service;

import com.ttn.project2.ExceptionHandler.ResourceNotFoundException;
import com.ttn.project2.Model.Product;
import com.ttn.project2.Model.User;
import com.ttn.project2.Model.UserAddress;
import com.ttn.project2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    //Add Product
    public String addProduct(Product product) {
        productRepository.save(product);
        return "Product added Successfully";

    }

    //Get Product
    public List<Product> viewProduct(@PathVariable long id) throws ResourceNotFoundException {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id : " + id + " is not found"));

        return productRepository.findAll();
    }

    //Delete Product
    public String deleteProduct(@PathVariable long id) {

        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            productRepository.deleteById(id);
            return "Product Deleted Successfully";
        } else
            return "Product Id " + id + " not found ! ";
    }

}




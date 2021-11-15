package com.ttn.project2.controller;

import com.ttn.project2.Service.AdminService;
import com.ttn.project2.Service.CustomerService;
import com.ttn.project2.Service.SellerService;
import com.ttn.project2.Model.Customer;
import com.ttn.project2.ExceptionHandler.ResourceNotFoundException;
import com.ttn.project2.Model.Seller;
import com.ttn.project2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    AdminService admin_service;

    @Autowired
    CustomerService customerService;

    @Autowired
    SellerService sellerService;

    @Autowired
    UserRepository repo;


    //API to GET Customer
    @GetMapping("/get/customer")
    public List<Customer> findAllCustomer() {
        return admin_service.getCustomerr();
    }


    //API to GET Seller
    @GetMapping("/get/seller")
    public List<Seller> findAllSeller() {
        return admin_service.getSeller();
    }


    //API to Activate Customer
    @PutMapping("/activate/customer/{id}")
    public String activateCustomer(
            @PathVariable(value = "id") Long userId,
            @RequestBody Customer customer) throws ResourceNotFoundException {
        return customerService.activateCustomer(userId, customer);
    }

    //API to De-Activate Customer
    @PutMapping("/deactivate/customer/{id}")
    public String deActivateCustomer(
            @PathVariable(value = "id") Long userId,
            @RequestBody Customer customer) throws ResourceNotFoundException {
        return customerService.deActivateCustomer(userId, customer);
    }


    //API to Activate Seller
    @PutMapping("/activate/seller/{id}")
    public String activateSeller(
            @PathVariable(value = "id") Long userId,
            @RequestBody Seller seller) throws ResourceNotFoundException {
        return sellerService.activateSeller(userId, seller);
    }


    //API to De-Activate Seller
    @PutMapping("/deactivate/seller/{id}")
    public String deActivateSeller(
            @PathVariable(value = "id") Long userId,
            @RequestBody Seller seller) throws ResourceNotFoundException {
        return sellerService.deActivateSeller(userId, seller);
    }
}
package com.ttn.project2.controller;

import com.ttn.project2.Service.RegistrationService;
import com.ttn.project2.Model.Customer;
import com.ttn.project2.Model.Seller;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor

public class RegistrationController {

    @Autowired
    private final RegistrationService registrationService;

    //API For Customer Registration

    @PostMapping(path = "/customer")
    public String registerCustomer( @Valid @RequestBody Customer customer) {
        return registrationService.registerCustomer(customer);
    }

    //API For Seller Registration

    @PostMapping(path = "seller")
    public String registerSeller(@Valid @RequestBody Seller seller) {
        return registrationService.registerSeller(seller);
    }

    //API For token confirmation

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);

    }
}
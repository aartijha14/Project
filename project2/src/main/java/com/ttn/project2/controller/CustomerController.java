package com.ttn.project2.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ttn.project2.ExceptionHandler.ResourceNotFoundException;
import com.ttn.project2.Service.CustomerService;
import com.ttn.project2.Model.*;
import com.ttn.project2.Service.UserAddressService;
import com.ttn.project2.Service.UserService;
import com.ttn.project2.email.EmailSender;
import com.ttn.project2.repository.UserAddressRepository;
import com.ttn.project2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {

    @Autowired
    UserRepository repo;

    @Autowired
    CustomerService customerService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired

    UserAddressService userAddressService;

    @Autowired
    EmailSender emailSender;

    @Autowired
    UserAddressRepository userAddressRepository;


    //API to view profile
    @RequestMapping(value = "/customer/profile", method= RequestMethod.GET)
    MappingJacksonValue getCustomerProfile(){

        SimpleBeanPropertyFilter simpleBeanPropertyFilter =

        SimpleBeanPropertyFilter.serializeAllExcept( "email","middleName","password","confirm_password",
                                                        "invalidAttemptCount","resetToken","accessToken",
                                                        "address","role","locked","enabled",
                                                        "username","deleted","expired","authorities",
                                                        "accountNonExpired","accountNonLocked","credentialsNonExpired",
                                                        "date_created",
                                                        "last_updated");

        FilterProvider filterProvider = new SimpleFilterProvider()

                .addFilter("userFilter", simpleBeanPropertyFilter);

        List<Customer> userList = customerService.getCostomerProfile();

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userList);

        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;

    }


    //API to view Address
    @GetMapping("/customer/address/{id}")
    //@JsonView(Views.MyResponseViews.class)

    public List<UserAddress> viewAddress(@PathVariable("id") long id) {
        return userAddressService.viewAddress(id);
    }


    //API To Update Customer Profile
    @PatchMapping("/customer/{id}")
    public @ResponseBody
    String saveCustomer(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("userId : " + id + " is not found"));

        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Customer.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, v);
        });
        userService.save(user);
        return "Profile Updated Successfully";

    }


    //API to Update Password
    @PatchMapping("/customer/password/{id}")
    public @ResponseBody
    String updatePassword(@PathVariable Long id, @Valid @RequestBody Map<Object, Object> fields) {
        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("userId : " + id + " is not found"));

        //if (!user.getPassword().equals(user.getConfirm_password())) {
        //   return "Password and Confirm-Password does not match";
        // }

        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Customer.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, v);
        });

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirm_password(passwordEncoder.encode(user.getConfirm_password()));
        userService.save(user);

        String message = " Your Password is Successfully Changed ! ";

        //Send Email For Password Updation
        emailSender.send(
                user.getEmail(), buildEmail(user.getFirstName(), message));

        return "Password Updated Successfully";
    }

    //Email
    private String buildEmail(String name, String message) {
        return "Hyy " + name + message;
    }


    //API to Add New Address
    @PostMapping(path = "/add/address")
    public String addAddress(@RequestBody UserAddress userAddress) {
        return userAddressService.addAddress(userAddress);
    }

    //API to delete Address
    @DeleteMapping("/customer/{id}")
    public String deleteAddress(@PathVariable long id) {
        return userAddressService.deleteAddress(id);
    }


    //API to Update Address
    @PatchMapping("/customer/address/{id}")
    public @ResponseBody
    String updateAddress(@PathVariable Long id, @Valid @RequestBody Map<Object, Object> fields) {

        UserAddress useradd = userAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address Id : " + id + " is not found"));

        //if (!user.getPassword().equals(user.getConfirm_password())) {
        //   return "Password and Confirm-Password does not match";
        // }

        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(UserAddress.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, useradd, v);
        });
        userAddressService.save(useradd);
        return "Address Updated Successfully";
    }
}
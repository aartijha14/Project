package com.ttn.project2.controller;
import com.ttn.project2.Model.*;

import com.ttn.project2.ExceptionHandler.ResourceNotFoundException;
import com.ttn.project2.Service.SellerService;
import com.ttn.project2.Model.User;
import com.ttn.project2.Service.UserAddressService;
import com.ttn.project2.Service.UserService;
import com.ttn.project2.email.EmailSender;
import com.ttn.project2.email.EmailValidator;
import com.ttn.project2.repository.UserAddressRepository;
import com.ttn.project2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
public class SellerController {

    @Autowired
    SellerService sellerService;

    @Autowired
    UserService userService;

    @Autowired
    UserAddressService userAddressService;

    @Autowired
    UserRepository repo;

    @Autowired
    UserAddressRepository userAddressRepository;

    @Autowired
    EmailSender emailSender;

    @Autowired
    EmailValidator emailValidator;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    //API to View Profile
    @GetMapping("/seller/profile")
    public List<Seller> findseller() {
        return sellerService.findSeller();
    }


    //API to Update Profile
    @PatchMapping("/seller/{id}")
    public @ResponseBody
    String saveSeller(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("userId : " + id + " is not found"));

        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Seller.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, v);
        });
        userService.save(user);
        return "Profile Updated Successfully";
        //return new ResponseEntity<>(user, HttpStatus.OK);

    }


    //API to Update Password
    @PatchMapping("/seller/password/{id}")
    public @ResponseBody
    String updatePassword(@PathVariable Long id, @Valid @RequestBody Map<Object, Object> fields) {
        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("userId : " + id + " is not found"));

        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Seller.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, v);
        });

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirm_password(passwordEncoder.encode(user.getConfirm_password()));
        userService.save(user);

        String message = " Your Password is Successfully Changed ! ";

       // Send Email
        emailSender.send(
                user.getEmail(), buildEmail(user.getFirstName(), message));

        return "Password Updated Successfully";
    }

    //Email
    private String buildEmail(String name, String message) {
        return "Hyy " + name + message;
    }


    //API to update Address
    @PatchMapping("/seller/address/{id}")
    public @ResponseBody
    String updateAddress(@PathVariable Long id, @Valid @RequestBody Map<Object, Object> fields) {

        UserAddress useradd = userAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address Id : " + id + " is not found"));

        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(UserAddress.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, useradd, v);
        });
        userAddressService.save(useradd);
        return "Address Updated Successfully";
    }
}
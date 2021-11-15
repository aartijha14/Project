package com.ttn.project2.controller;

import com.ttn.project2.Service.UserService;
import com.ttn.project2.Model.User;
import com.ttn.project2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserController {

    @Autowired
    UserRepository repo;

    @Autowired
    UserService service;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    //API for User Login

    @PostMapping("/users/login")
    public String loginUser(@RequestBody User user) {
        return service.loginUser(user);
    }

    //API for User Logout

    @PostMapping("/users/logout")
    public String logoutUser(@RequestBody User user) {
        return service.logoutUser(user);
    }


    //API for forget password

    @PostMapping(path = "/forgot")
    public String forgotPassword(@RequestBody User user) {
        return service.forgotPassword(user);
    }


    //API for reset password

    @PostMapping(path = "/reset")
    public String resetPassword(@RequestBody User user) {
        return service.resetToken(user);
    }
}
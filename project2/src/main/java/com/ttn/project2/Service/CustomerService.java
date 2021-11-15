package com.ttn.project2.Service;

import com.ttn.project2.Model.Customer;
import com.ttn.project2.ExceptionHandler.ResourceNotFoundException;
import com.ttn.project2.Model.Role;
import com.ttn.project2.Model.User;
import com.ttn.project2.config.UserDetailsServiceImpl;
import com.ttn.project2.email.EmailSender;
import com.ttn.project2.email.EmailValidator;
import com.ttn.project2.repository.*;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    @Autowired
    UserRepository repo;

    @Autowired
    CustomerRepository customer_repo;

    @Autowired
    RoleRepository role_repo;

    @Autowired
    ConfirmationTokenService tokenService;

    @Autowired
    ResetTokenService resetTokenService;

    @Autowired
    ResetTokenRepo resetRepo;

    @Autowired
    ConfirmationTokenRepo tokenRepo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    EmailSender emailSender;

    @Autowired
    EmailValidator emailValidator;

    @Autowired
    UserDetailsServiceImpl detailsService;


    //To Activate Customer
    public String activateCustomer(
            @PathVariable(value = "id") Long userId,
            @RequestBody Customer customer) throws ResourceNotFoundException {
        User user = repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("userId : " + userId + " is not found"));

        if (user.isActive() == false) {

            user.setActive(customer.isActive());
            final User activatedCustomer = repo.save(user);

            String message=" Your account is successfully Activated";

            //Send Email
            emailSender.send(
                    user.getEmail(),buildEmail(user.getFirstName(), message));;

            return "Customer Successfully Activated";

        } else {
            return "Customer Already Activated";
        }
    }

    //To De-Activate Customer
    public String deActivateCustomer(
            @PathVariable(value = "id") Long userId,
            @RequestBody Customer customer) throws ResourceNotFoundException {
        User user = repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("userId : " + userId + " is not found"));

        if (user.isActive() == true) {
        user.setActive(customer.isActive());

        final User deActivatedCustomer = repo.save(user);

            String message=" Your account is successfully De-Activated";

            //Send Email
            emailSender.send(
                    user.getEmail(),buildEmail(user.getFirstName(), message));;

        return "Customer Successfully De-Activated";
        } else {
            return "Customer Already De-Activated";
        }
    }

    //Email
    private String buildEmail(String name, String message) {
        return "Hyy "+ name + message;
    }



    //Find Customers
    public List<Customer> findCustomer(){
        return this.customer_repo.findAll();
    }

    //View Customer Profile
    public List<Customer> getCostomerProfile(){

        return this.customer_repo.findAll();
    }

}
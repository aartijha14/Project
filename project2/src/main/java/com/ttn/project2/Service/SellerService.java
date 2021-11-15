package com.ttn.project2.Service;

import com.ttn.project2.ExceptionHandler.ResourceNotFoundException;
import com.ttn.project2.Model.Seller;
import com.ttn.project2.Model.User;
import com.ttn.project2.email.EmailSender;
import com.ttn.project2.email.EmailValidator;
import com.ttn.project2.repository.CustomerRepository;
import com.ttn.project2.repository.SellerRepository;
import com.ttn.project2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class SellerService {

    @Autowired
    UserRepository repo;

    @Autowired
    SellerRepository seller_repo;

    @Autowired
    CustomerRepository customer_repo;

    @Autowired
    EmailSender emailSender;

    @Autowired
    EmailValidator emailValidator;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    User user;


    //Activate Seller
    public String activateSeller(
            @PathVariable(value = "id") Long userId,
            @RequestBody Seller seller) throws ResourceNotFoundException {
        User user = repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("userId : " + userId + " is not found"));
        if (user.isActive() == false) {
            user.setActive(seller.isActive());

            final User updatedSeller = repo.save(user);

            String message = " Your account is successfully Activated";

            //Send Email
            emailSender.send(
                    user.getEmail(), buildEmail(user.getFirstName(), message));

            return "Seller Successfully Activated";
        } else {
            return "Seller Already Activated";
        }
    }

    //De-activate Seller
    public String deActivateSeller(
            @PathVariable(value = "id") Long userId,
            @RequestBody Seller seller) throws ResourceNotFoundException {
        User user = repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("userId : " + userId + " is not found"));
        if (user.isActive() == true) {
            user.setActive(seller.isActive());

            final User deActivatedSeller = repo.save(user);
            String message = " Your account is successfully De-Activated";

            //Send email
            emailSender.send(
                    user.getEmail(), buildEmail(user.getFirstName(), message));

            return "Seller Successfully De-Activated";
        } else {
            return "Seller Already De-Activated";
        }
    }

    //Email
    private String buildEmail(String name, String message) {
        return "Hyy " + name + message;
    }


    //Find seller
    public List<Seller> findSeller() {

        return this.seller_repo.findAll();
    }

    //Save seller
    public void save(Seller seller) {
        repo.save(seller);
    }

}
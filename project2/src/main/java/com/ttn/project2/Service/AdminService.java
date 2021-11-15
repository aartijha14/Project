package com.ttn.project2.Service;

import com.ttn.project2.Model.Customer;
import com.ttn.project2.Model.Seller;
import com.ttn.project2.repository.CustomerRepository;
import com.ttn.project2.repository.SellerRepository;
import com.ttn.project2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository user_repo;

    @Autowired
    CustomerRepository customer_repo;

    @Autowired
    SellerRepository seller_repo;


    //To Get All Customers
    public List<Customer> getCustomerr(){
        return this.customer_repo.findAll();
    }


    //To Get all Sellers
    public List<Seller> getSeller(){
        return this.seller_repo.findAll();
    }
}
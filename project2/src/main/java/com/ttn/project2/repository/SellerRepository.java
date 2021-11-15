package com.ttn.project2.repository;

import com.ttn.project2.Model.Seller;
import com.ttn.project2.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SellerRepository extends JpaRepository<Seller,Long> {
    //@Query("select u from User u where u.email=:email")
    //public User getUserByUserName(@Param("email")String email);
}

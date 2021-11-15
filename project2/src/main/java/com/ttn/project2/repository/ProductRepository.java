package com.ttn.project2.repository;

import com.ttn.project2.Model.Customer;
import com.ttn.project2.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

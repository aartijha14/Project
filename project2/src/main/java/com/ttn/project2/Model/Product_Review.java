package com.ttn.project2.Model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity

public class Product_Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long customer_user_id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Customer customer;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long product_id;

    @OneToOne
    @JoinColumn(name = "id")
    private Product product;

    private String review;
    private int rating;

}
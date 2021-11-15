package com.ttn.project2.Model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "order")
    private List<Order_Product> order_product = new ArrayList<Order_Product>();

    private double amount_paid;

    @CreationTimestamp
    private Date date_created;
    private String payment_method;
    private String customer_address_city;
    private String customer_address_state;
    private String customer_address_country;
    private String customer_address_address_line;
    private String customer_address_zip_code;
    private String customer_address_label;

}

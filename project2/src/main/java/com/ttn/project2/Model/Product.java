package com.ttn.project2.Model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_seller", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "seller_id", referencedColumnName = "user_id"))

    private List<Seller> sellers = new ArrayList<Seller>();

    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Product_Variation> product_variations = new ArrayList<Product_Variation>();

    private String name;
    private String description;
    private boolean is_cancellable;
    private boolean is_returnable;
    private String brand;
    private boolean is_active;

}
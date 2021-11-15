package com.ttn.project2.Model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity

public class Product_Variation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private int quantity_available;
    private double price;
    private boolean is_active;

    @Column(columnDefinition = "JSON")
    private String metadata;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Transient
    private MultipartFile primaryImageName;

}
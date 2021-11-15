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
public class Category {
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private  String name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "category")
    private List<Product> product = new ArrayList<Product>();

}

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

public class Category_Metadta_Field_Values implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long category_metadata_field_id;

    @OneToOne
    @JoinColumn(name = "id")
    private Category_Metadata_Field category_metadata_field;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long category_id;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String values[];

}
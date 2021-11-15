package com.ttn.project2.Model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor

@Entity
@Table(name = "seller")
@PrimaryKeyJoinColumn(name = "user_id")

public class Seller extends User {

    @Column(name = "gst_no")
    private String gstNo;

    @Column(name = "company_contact")
    private Long companyContact;

    @Column(name = "company_name")
    private String companyName;

}

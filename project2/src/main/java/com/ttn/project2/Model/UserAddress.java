package com.ttn.project2.Model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Component
@Table(name="user_address")
@Entity
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String city;
    private String state;
    private String country;
    private String address_line;
    private String zip_code;
    private String label;

   /*@ManyToOne()
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;*/

}




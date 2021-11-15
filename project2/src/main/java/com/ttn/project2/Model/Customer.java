package com.ttn.project2.Model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Component
@Table(name = "customer")

@PrimaryKeyJoinColumn(name = "user_id")

@JsonFilter("userFilter")                   //Dynamic Filtering

public class Customer extends User{

    @Pattern(regexp="(^$|[0-9]{10})",message = "Please provide valid contact number")
    private String contact;

}
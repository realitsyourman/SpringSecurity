package com.security.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter @Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long id;

    private String name;

    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pwd;

    private String role;

    @Column(name = "create_dt")
    @JsonIgnore
    private Date createDt;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

}

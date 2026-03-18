package com.beuticlick.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
public class Customer extends BaseSalonEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String city;
    private String email;

}

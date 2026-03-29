package com.beuticlick.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "staff")
@Getter
@Setter
public class Staff extends BaseSalonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;
    private String role;         // e.g. "Hair Stylist", "Nail Tech", "Manager"
    private Boolean available;   // whether they are currently taking appointments

}

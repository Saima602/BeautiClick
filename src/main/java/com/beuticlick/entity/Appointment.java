package com.beuticlick.entity;

import java.time.LocalDateTime;

import com.beuticlick.constant.StatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private String notes;

    // relationships
    @ManyToOne
    private Staff staff;

    @ManyToOne
    private SalonService service;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "salon_id")
    private Salon salon;
}
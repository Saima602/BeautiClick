package com.beuticlick.entity;

import java.time.LocalDateTime;

import com.beuticlick.constant.StatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "appointments")
@Getter
@Setter
public class Appointment extends BaseSalonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Customer customer;

	@ManyToOne
	private Staff staff;

	@ManyToOne
	private SalonService service;

	private LocalDateTime startTime;
	private LocalDateTime endTime;

	// Captured at booking time so billing is always accurate even if price changes
	// later
	private Double servicePrice;

	@Enumerated(EnumType.STRING)
	private StatusEnum status;
}

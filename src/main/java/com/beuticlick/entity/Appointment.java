package com.beuticlick.entity;

import java.time.LocalDateTime;

import com.beuticlick.constant.StatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	private Long customerId;
	private Long serviceId;
	private Long staffId;

	private LocalDateTime appointmentTime;

	@Enumerated(EnumType.STRING)
	private StatusEnum status;
}
package com.beuticlick.dto.request;

import java.time.LocalDateTime;

import com.beuticlick.entity.Customer;
import com.beuticlick.entity.SalonService;
import com.beuticlick.entity.Staff;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentRequest {

	@NotNull(message = "Customer ID is required")
	private Customer customer;

	@NotNull(message = "Service ID is required")
	private SalonService service;

	@NotNull(message = "Staff ID is required")
	private Staff staff;

	@NotNull(message = "Appointment time is required")
	@Future(message = "Appointment time must be in the future")
	private LocalDateTime appointmentTime;

}

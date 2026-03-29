package com.beuticlick.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "salon_services")
@Getter
@Setter
public class SalonService extends BaseSalonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name; // e.g. "Haircut", "Manicure", "Hair Colour"
	private String category; // e.g. "Hair", "Nails", "Skin"
	private String description;
	private Double price;
	private Integer durationMinutes; // how long the appointment slot should be
	private Boolean active; // soft-disable without deleting

}

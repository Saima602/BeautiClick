package com.beuticlick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beuticlick.entity.Appointment;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    List<Appointment> findBySalonId(Long salonId);

    List<Appointment> findBySalonIdAndCustomerId(Long salonId, Long customerId);

}

package com.beuticlick.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beuticlick.entity.Appointment;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

}

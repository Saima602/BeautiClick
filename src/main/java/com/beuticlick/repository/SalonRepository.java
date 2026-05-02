package com.beuticlick.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beuticlick.entity.Salon;
import java.util.Optional;

public interface SalonRepository extends JpaRepository<Salon, Long> {
    Optional<Salon> findBySalonCode(String salonCode);
}

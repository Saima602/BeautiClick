package com.beuticlick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beuticlick.entity.SalonService;

public interface SalonServiceRepository extends JpaRepository<SalonService, Long> {

    List<SalonService> findBySalonId(Long salonId);

    List<SalonService> findBySalonIdAndActive(Long salonId, Boolean active);

    List<SalonService> findBySalonIdAndCategory(Long salonId, String category);

}

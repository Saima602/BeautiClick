package com.beuticlick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beuticlick.constant.StaffRole;
import com.beuticlick.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    List<Staff> findBySalonId(Long salonId);

    List<Staff> findBySalonIdAndAvailable(Long salonId, Boolean available);
    List<Staff> findBySalonIdAndRole(Long salonId, StaffRole role);

}

package com.beuticlick.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beuticlick.constant.StaffRole;
import com.beuticlick.entity.Staff;
import com.beuticlick.repository.StaffRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository repository;

    public Staff create(Staff staff, Long salonId) {
        staff.setSalonId(salonId);
        staff.setAvailable(true); // new staff is available by default
        return repository.save(staff);
    }

    public List<Staff> getAll(Long salonId) {
        return repository.findBySalonId(salonId);
    }

    public List<Staff> getAvailable(Long salonId) {
        return repository.findBySalonIdAndAvailable(salonId, true);
    }

    public List<Staff> getByRole(Long salonId, String role) {
        return repository.findBySalonIdAndRole(salonId, StaffRole.valueOf(role));
    }

    public Staff getById(Long id, Long salonId) {
        Staff staff = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Staff not found: " + id));
        if (!staff.getSalonId().equals(salonId)) {
            throw new RuntimeException("Staff " + id + " does not belong to salon " + salonId);
        }
        return staff;
    }

    public Boolean isAvailable(Long id, Long salonId) {
        return getById(id, salonId).getAvailable();
    }

    @Transactional
    public Staff update(Long id, Staff updated, Long salonId) {
        Staff existing = getById(id, salonId);
        // Always update required fields
        existing.setName(updated.getName());
        existing.setPhone(updated.getPhone());
        existing.setRole(updated.getRole());
        // Only update optional fields if provided (not null)
        if (updated.getEmail() != null) {
            existing.setEmail(updated.getEmail());
        }
        if (updated.getSpecialization() != null) {
            existing.setSpecialization(updated.getSpecialization());
        }
        if (updated.getExperienceYears() != null) {
            existing.setExperienceYears(updated.getExperienceYears());
        }
        if (updated.getAvailable() != null) {
            existing.setAvailable(updated.getAvailable());
        }
        return repository.save(existing);
    }

    public void delete(Long id, Long salonId) {
        Staff existing = getById(id, salonId);
        repository.delete(existing);
    }

}

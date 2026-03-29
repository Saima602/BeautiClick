package com.beuticlick.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

    public Staff getById(Long id, Long salonId) {
        Staff staff = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Staff not found: " + id));
        if (!staff.getSalonId().equals(salonId)) {
            throw new RuntimeException("Staff " + id + " does not belong to salon " + salonId);
        }
        return staff;
    }

    public Staff update(Long id, Staff updated, Long salonId) {
        Staff existing = getById(id, salonId);
        existing.setName(updated.getName());
        existing.setPhone(updated.getPhone());
        existing.setEmail(updated.getEmail());
        existing.setRole(updated.getRole());
        existing.setAvailable(updated.getAvailable());
        return repository.save(existing);
    }

    public void delete(Long id, Long salonId) {
        Staff existing = getById(id, salonId);
        repository.delete(existing);
    }

}

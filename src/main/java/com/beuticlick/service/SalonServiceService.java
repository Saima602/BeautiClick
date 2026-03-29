package com.beuticlick.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.beuticlick.entity.SalonService;
import com.beuticlick.repository.SalonServiceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalonServiceService {

    private final SalonServiceRepository repository;

    public SalonService create(SalonService salonService, Long salonId) {
        salonService.setSalonId(salonId);
        salonService.setActive(true); // new services are active by default
        return repository.save(salonService);
    }

    public List<SalonService> getAll(Long salonId) {
        return repository.findBySalonId(salonId);
    }

    public List<SalonService> getActive(Long salonId) {
        return repository.findBySalonIdAndActive(salonId, true);
    }

    public List<SalonService> getByCategory(Long salonId, String category) {
        return repository.findBySalonIdAndCategory(salonId, category);
    }

    public SalonService getById(Long id, Long salonId) {
        SalonService salonService = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Service not found: " + id));
        if (!salonService.getSalonId().equals(salonId)) {
            throw new RuntimeException("Service " + id + " does not belong to salon " + salonId);
        }
        return salonService;
    }

    public SalonService update(Long id, SalonService updated, Long salonId) {
        SalonService existing = getById(id, salonId);
        existing.setName(updated.getName());
        existing.setCategory(updated.getCategory());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setDurationMinutes(updated.getDurationMinutes());
        existing.setActive(updated.getActive());
        return repository.save(existing);
    }

    public void delete(Long id, Long salonId) {
        SalonService existing = getById(id, salonId);
        // soft delete — just mark inactive rather than removing the row
        // so historical appointments that reference this service still make sense
        existing.setActive(false);
        repository.save(existing);
    }

}

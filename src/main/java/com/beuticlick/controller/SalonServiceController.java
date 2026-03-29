package com.beuticlick.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.beuticlick.entity.SalonService;
import com.beuticlick.service.SalonServiceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class SalonServiceController {

    private final SalonServiceService service;

    // POST /services — add a new treatment/service
    @PostMapping
    public SalonService create(@RequestBody SalonService salonService,
                               @RequestHeader("salonId") Long salonId) {
        return service.create(salonService, salonId);
    }

    // GET /services — all services for this salon (including inactive)
    @GetMapping
    public List<SalonService> getAll(@RequestHeader("salonId") Long salonId) {
        return service.getAll(salonId);
    }

    // GET /services/active — only bookable services (shown to customers)
    @GetMapping("/active")
    public List<SalonService> getActive(@RequestHeader("salonId") Long salonId) {
        return service.getActive(salonId);
    }

    // GET /services/category/{category} — filter by category e.g. "Hair", "Nails"
    @GetMapping("/category/{category}")
    public List<SalonService> getByCategory(@PathVariable String category,
                                            @RequestHeader("salonId") Long salonId) {
        return service.getByCategory(salonId, category);
    }

    // GET /services/{id}
    @GetMapping("/{id}")
    public SalonService getById(@PathVariable Long id,
                                @RequestHeader("salonId") Long salonId) {
        return service.getById(id, salonId);
    }

    // PUT /services/{id} — update price, duration, name etc.
    @PutMapping("/{id}")
    public SalonService update(@PathVariable Long id,
                               @RequestBody SalonService salonService,
                               @RequestHeader("salonId") Long salonId) {
        return service.update(id, salonService, salonId);
    }

    // DELETE /services/{id} — soft deletes (marks inactive, row kept for billing history)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @RequestHeader("salonId") Long salonId) {
        service.delete(id, salonId);
    }

}

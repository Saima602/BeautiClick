package com.beuticlick.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.beuticlick.entity.Staff;
import com.beuticlick.service.StaffService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService service;

    // POST /staff — add a new staff member
    @PostMapping
    public Staff create(@RequestBody Staff staff,
                        @RequestHeader("salonId") Long salonId) {
        return service.create(staff, salonId);
    }

    // GET /staff — all staff for this salon
    @GetMapping
    public List<Staff> getAll(@RequestHeader("salonId") Long salonId) {
        return service.getAll(salonId);
    }

    // GET /staff/available — only staff currently taking appointments
    @GetMapping("/available")
    public List<Staff> getAvailable(@RequestHeader("salonId") Long salonId) {
        return service.getAvailable(salonId);
    }

    // GET /staff/{id} — single staff member
    @GetMapping("/{id}")
    public Staff getById(@PathVariable Long id,
                         @RequestHeader("salonId") Long salonId) {
        return service.getById(id, salonId);
    }

    // PUT /staff/{id} — update details or toggle availability
    @PutMapping("/{id}")
    public Staff update(@PathVariable Long id,
                        @RequestBody Staff staff,
                        @RequestHeader("salonId") Long salonId) {
        return service.update(id, staff, salonId);
    }

    // DELETE /staff/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @RequestHeader("salonId") Long salonId) {
        service.delete(id, salonId);
    }

}

package com.beuticlick.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.beuticlick.entity.Appointment;
import com.beuticlick.service.AppointmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    // POST /appointments — book a new appointment
    @PostMapping
    public Appointment book(@RequestBody Appointment appointment,
                            @RequestHeader("salonId") Long salonId) {
        return service.book(appointment, salonId);
    }

    // GET /appointments — all appointments for this salon
    @GetMapping
    public List<Appointment> getAll(@RequestHeader("salonId") Long salonId) {
        return service.getAll(salonId);
    }

    // GET /appointments/{id}
    @GetMapping("/{id}")
    public Appointment getById(@PathVariable Long id,
                               @RequestHeader("salonId") Long salonId) {
        return service.getById(id, salonId);
    }

    // GET /appointments/customer/{customerId} — all appointments for one customer
    @GetMapping("/customer/{customerId}")
    public List<Appointment> getByCustomer(@PathVariable Long customerId,
                                           @RequestHeader("salonId") Long salonId) {
        return service.getByCustomer(customerId, salonId);
    }

    // PATCH /appointments/{id}/complete — mark done, auto-bills and deducts stock
    @PatchMapping("/{id}/complete")
    public void complete(@PathVariable Long id,
                         @RequestHeader("salonId") Long salonId) {
        service.completeAppointment(id, salonId);
    }

    // PATCH /appointments/{id}/cancel
    @PatchMapping("/{id}/cancel")
    public Appointment cancel(@PathVariable Long id,
                              @RequestHeader("salonId") Long salonId) {
        return service.cancel(id, salonId);
    }

}

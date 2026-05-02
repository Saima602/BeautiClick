package com.beuticlick.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.beuticlick.dto.request.AppointmentRequest;
import com.beuticlick.dto.response.AppointmentResponse;
import com.beuticlick.dto.AppointmentMapper;
import com.beuticlick.security.SecurityUtils;
import com.beuticlick.service.AppointmentService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AppointmentController {

    private final AppointmentService service;

    // ✅ Create Appointment
    @PostMapping
    public AppointmentResponse create(@Valid @RequestBody AppointmentRequest request) {
        return AppointmentMapper.toResponse(
                service.book(request, SecurityUtils.currentSalonId())
        );
    }

    // ✅ Get All Appointments (for current salon)
    @GetMapping
    public List<AppointmentResponse> getAll() {
        return service.getAll(SecurityUtils.currentSalonId())
                .stream()
                .map(AppointmentMapper::toResponse)
                .toList();
    }

    // ✅ Get by ID
    @GetMapping("/{id}")
    public AppointmentResponse getById(@PathVariable Long id) {
        return AppointmentMapper.toResponse(
                service.getById(id, SecurityUtils.currentSalonId())
        );
    }

    // ✅ Get appointments by date
    @GetMapping("/date")
    public List<AppointmentResponse> getByDate(@RequestParam LocalDate date) {
        return service.getByDate(date, SecurityUtils.currentSalonId())
                .stream()
                .map(AppointmentMapper::toResponse)
                .toList();
    }

    // ❌ Cancel Appointment
    @PatchMapping("/{id}/cancel")
    public AppointmentResponse cancel(@PathVariable Long id) {
        return AppointmentMapper.toResponse(
                service.cancel(id, SecurityUtils.currentSalonId())
        );
    }

    // ✅ Complete Appointment
    @PatchMapping("/{id}/complete")
    public AppointmentResponse complete(@PathVariable Long id) {
        return AppointmentMapper.toResponse(
                service.complete(id, SecurityUtils.currentSalonId())
        );
    }
}
package com.beuticlick.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import com.beuticlick.dto.DtoMapper;
import com.beuticlick.dto.request.AppointmentRequest;
import com.beuticlick.dto.response.AppointmentResponse;
import com.beuticlick.security.SecurityUtils;
import com.beuticlick.service.AppointmentService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    public AppointmentResponse book(@Valid @RequestBody AppointmentRequest request) {
        return DtoMapper.toResponse(
            service.book(DtoMapper.toEntity(request), SecurityUtils.currentSalonId())
        );
    }

    @GetMapping
    public List<AppointmentResponse> getAll() {
        return service.getAll(SecurityUtils.currentSalonId()).stream()
            .map(DtoMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AppointmentResponse getById(@PathVariable Long id) {
        return DtoMapper.toResponse(service.getById(id, SecurityUtils.currentSalonId()));
    }

    @GetMapping("/customer/{customerId}")
    public List<AppointmentResponse> getByCustomer(@PathVariable Long customerId) {
        return service.getByCustomer(customerId, SecurityUtils.currentSalonId()).stream()
            .map(DtoMapper::toResponse).collect(Collectors.toList());
    }

    @PatchMapping("/{id}/complete")
    public void complete(@PathVariable Long id) {
        service.completeAppointment(id, SecurityUtils.currentSalonId());
    }

    @PatchMapping("/{id}/cancel")
    public AppointmentResponse cancel(@PathVariable Long id) {
        return DtoMapper.toResponse(service.cancel(id, SecurityUtils.currentSalonId()));
    }
}

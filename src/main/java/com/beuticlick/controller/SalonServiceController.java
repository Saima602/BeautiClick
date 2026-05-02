package com.beuticlick.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import com.beuticlick.dto.DtoMapper;
import com.beuticlick.dto.request.SalonServiceRequest;
import com.beuticlick.dto.response.SalonServiceResponse;
import com.beuticlick.security.SecurityUtils;
import com.beuticlick.service.SalonServiceService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/services")
@RequiredArgsConstructor
public class SalonServiceController {

    private final SalonServiceService service;

    @PostMapping
    public SalonServiceResponse create(@Valid @RequestBody SalonServiceRequest request) {
        return DtoMapper.toResponse(
            service.create(DtoMapper.toEntity(request), request.getSalonCode(), SecurityUtils.currentSalonId())
        );
    }

    @GetMapping
    public List<SalonServiceResponse> getAll() {
        return service.getAll(SecurityUtils.currentSalonId()).stream()
            .map(DtoMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/active")
    public List<SalonServiceResponse> getActive() {
        return service.getActive(SecurityUtils.currentSalonId()).stream()
            .map(DtoMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/category/{category}")
    public List<SalonServiceResponse> getByCategory(@PathVariable String category) {
        return service.getByCategory(SecurityUtils.currentSalonId(), category).stream()
            .map(DtoMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SalonServiceResponse getById(@PathVariable Long id) {
        return DtoMapper.toResponse(service.getById(id, SecurityUtils.currentSalonId()));
    }

    @PutMapping("/{id}")
    public SalonServiceResponse update(@PathVariable Long id,
                                       @Valid @RequestBody SalonServiceRequest request) {
        return DtoMapper.toResponse(
            service.update(id, DtoMapper.toEntity(request), SecurityUtils.currentSalonId())
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id, SecurityUtils.currentSalonId());
    }
}

package com.beuticlick.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import com.beuticlick.dto.DtoMapper;
import com.beuticlick.dto.request.StaffRequest;
import com.beuticlick.dto.response.StaffResponse;
import com.beuticlick.security.SecurityUtils;
import com.beuticlick.service.StaffService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService service;

    @PostMapping
    public StaffResponse create(@Valid @RequestBody StaffRequest request) {
        return DtoMapper.toResponse(
            service.create(DtoMapper.toEntity(request), SecurityUtils.currentSalonId())
        );
    }

    @GetMapping
    public List<StaffResponse> getAll() {
        return service.getAll(SecurityUtils.currentSalonId()).stream()
            .map(DtoMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/available")
    public List<StaffResponse> getAvailable() {
        return service.getAvailable(SecurityUtils.currentSalonId()).stream()
            .map(DtoMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public StaffResponse getById(@PathVariable Long id) {
        return DtoMapper.toResponse(service.getById(id, SecurityUtils.currentSalonId()));
    }

    @PutMapping("/{id}")
    public StaffResponse update(@PathVariable Long id, @Valid @RequestBody StaffRequest request) {
        return DtoMapper.toResponse(
            service.update(id, DtoMapper.toEntity(request), SecurityUtils.currentSalonId())
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id, SecurityUtils.currentSalonId());
    }
}

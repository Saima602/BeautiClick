package com.beuticlick.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import com.beuticlick.dto.DtoMapper;
import com.beuticlick.dto.request.CustomerRequest;
import com.beuticlick.dto.response.CustomerResponse;
import com.beuticlick.security.SecurityUtils;
import com.beuticlick.service.CustomerService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public CustomerResponse create(@Valid @RequestBody CustomerRequest request) {
        return DtoMapper.toResponse(
            service.create(DtoMapper.toEntity(request), request.getSalonCode(), SecurityUtils.currentSalonId())
        );
    }

    @GetMapping
    public List<CustomerResponse> getAll() {
        return service.getAll(SecurityUtils.currentSalonId()).stream()
            .map(DtoMapper::toResponse).collect(Collectors.toList());
    }
}

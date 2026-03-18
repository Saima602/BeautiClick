package com.beuticlick.controller;

import com.beuticlick.entity.Customer;
import com.beuticlick.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public Customer create(@RequestBody Customer customer,
                           @RequestHeader("salonId") Long salonId) {
        return service.create(customer, salonId);
    }

    @GetMapping
    public List<Customer> getAll(@RequestHeader("salonId") Long salonId) {
        return service.getAll(salonId);
    }
}

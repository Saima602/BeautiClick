package com.beuticlick.service;

import com.beuticlick.entity.Customer;
import com.beuticlick.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public Customer create(Customer customer, Long salonId) {
        customer.setSalonId(salonId);
        return repository.save(customer);
    }

    public List<Customer> getAll(Long salonId) {
        return repository.findBySalonId(salonId);
    }
}
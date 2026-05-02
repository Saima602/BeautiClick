package com.beuticlick.service;

import com.beuticlick.entity.Customer;
import com.beuticlick.entity.Salon;
import com.beuticlick.repository.CustomerRepository;
import com.beuticlick.repository.SalonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final SalonRepository salonRepository;

    public Customer create(Customer customer, String salonCode, Long currentSalonId) {
        if (salonCode != null && !salonCode.isBlank()) {
            Salon salon = salonRepository.findBySalonCode(salonCode)
                    .orElseThrow(() -> new RuntimeException("Salon not found for code: " + salonCode));
            customer.setSalonId(salon.getId());
        } else {
            customer.setSalonId(currentSalonId);
        }
        return repository.save(customer);
    }

    public List<Customer> getAll(Long salonId) {
        return repository.findBySalonId(salonId);
    }
}
package com.beuticlick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beuticlick.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findBySalonId(Long salonId);
}
